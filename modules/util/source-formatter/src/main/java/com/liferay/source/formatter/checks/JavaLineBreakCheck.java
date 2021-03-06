/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaLineBreakCheck extends LineBreakCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws CheckstyleException, IOException {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.startsWith(StringPool.SEMICOLON)) {
					addMessage(
						fileName, "Line should not start with ';'", lineNumber);
				}

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR) &&
					trimmedLine.startsWith(StringPool.PERIOD)) {

					addMessage(
						fileName, "Line should not start with '.'", lineNumber);
				}

				int lineLength = getLineLength(line);

				if (!line.startsWith("import ") &&
					!line.startsWith("package ") &&
					!trimmedLine.startsWith(StringPool.STAR) &&
					(lineLength <= getMaxLineLength())) {

					_checkLineBreaks(line, previousLine, fileName, lineNumber);
				}

				previousLine = line;
			}
		}

		content = _fixIncorrectLineBreaksInsideChains(content, fileName);

		content = _fixIncorrectLineBreaks(content, fileName);

		content = _fixLineStartingWithCloseParenthesis(content, fileName);

		content = _fixMultiLineComment(content);

		content = _fixClassOrEnumLineLineBreaks(content);

		content = fixRedundantCommaInsideArray(content);

		return content;
	}

	private void _checkLambdaLineBreaks(
		String line, String fileName, int lineNumber) {

		if (!line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			(getLevel(line) <= 0)) {

			return;
		}

		int pos = line.indexOf("->");

		if ((pos == -1) || ToolsUtil.isInsideQuotes(line, pos)) {
			return;
		}

		int x = 1;

		while (true) {
			String s = line.substring(0, x);

			if (getLevel(s) > 0) {
				addMessage(
					fileName, "There should be a line break after '" + s + "'",
					lineNumber);

				return;
			}

			x++;
		}
	}

	private void _checkLineBreaks(
		String line, String previousLine, String fileName, int lineNumber) {

		checkLineBreaks(line, previousLine, fileName, lineNumber);

		String trimmedLine = StringUtil.trimLeading(line);

		if (previousLine.contains("\t/*") || trimmedLine.startsWith("//") ||
			trimmedLine.startsWith("/*") || trimmedLine.endsWith("*/")) {

			return;
		}

		_checkLambdaLineBreaks(trimmedLine, fileName, lineNumber);

		if (trimmedLine.endsWith("( {")) {
			addMessage(
				fileName, "There should be a line before ' {'", lineNumber);
		}

		for (int x = 0;;) {
			x = trimmedLine.indexOf("}", x + 1);

			if (x == -1) {
				break;
			}

			if (!ToolsUtil.isInsideQuotes(trimmedLine, x) &&
				(getLevel(trimmedLine.substring(0, x + 1), "{", "}") < 0)) {

				addMessage(
					fileName,
					"There should be a line break after '" +
						trimmedLine.substring(0, x) + "'",
					lineNumber);
			}
		}

		if (previousLine.endsWith(StringPool.PERIOD)) {
			int x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS);

			if ((x != -1) &&
				((getLineLength(previousLine) + x) < getMaxLineLength()) &&
				!trimmedLine.startsWith("new ") &&
				(trimmedLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
				 (trimmedLine.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS))) {

				addMessage(fileName, "Incorrect line break", lineNumber);
			}
		}

		String strippedQuotesLine = stripQuotes(trimmedLine);

		if (line.matches(".*(\\(|->( \\{)?)")) {
			int x = line.lastIndexOf(" && ");
			int y = line.lastIndexOf(" || ");

			int z = Math.max(x, y);

			if (z != -1) {
				addMessage(
					fileName,
					"There should be a line break after '" +
						line.substring(z + 1, z + 3) + "'",
					lineNumber);
			}

			int pos = strippedQuotesLine.indexOf(" + ");

			if (pos != -1) {
				String linePart = strippedQuotesLine.substring(0, pos);

				if ((getLevel(linePart, "(", ")") == 0) &&
					(getLevel(linePart, "[", "]") == 0)) {

					addMessage(
						fileName, "There should be a line break after '+'",
						lineNumber);
				}
			}
		}

		if (line.matches(".*(\\(|\\^|\\&|\\||->( \\{)?)")) {
			int x = trimmedLine.length() + 1;

			while (true) {
				x = trimmedLine.lastIndexOf(StringPool.COMMA, x - 1);

				if (x == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					continue;
				}

				String linePart = trimmedLine.substring(
					x, trimmedLine.length() - 1);

				linePart = StringUtil.removeSubstring(linePart, "->");

				if ((getLevel(linePart) == 0) &&
					(getLevel(linePart, "<", ">") == 0)) {

					addMessage(
						fileName,
						"There should be a line break after '" +
							trimmedLine.substring(0, x + 1) + "'",
						lineNumber);

					break;
				}
			}
		}

		if (trimmedLine.matches("^[^(].*\\+$") && (getLevel(trimmedLine) > 0)) {
			addMessage(
				fileName, "There should be a line break after '('", lineNumber);
		}

		if (!trimmedLine.contains("\t//") && !line.endsWith("{") &&
			strippedQuotesLine.contains("{") &&
			!strippedQuotesLine.contains("}")) {

			addMessage(
				fileName, "There should be a line break after '{'", lineNumber);
		}

		if (line.endsWith(" throws") ||
			((previousLine.endsWith(StringPool.COMMA) ||
			  previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) &&
			 strippedQuotesLine.contains(" throws ") &&
			 (line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			  line.endsWith(StringPool.SEMICOLON)))) {

			addMessage(
				fileName, "There should be a line break before 'throws'",
				lineNumber);
		}

		if (line.endsWith(StringPool.PERIOD) &&
			strippedQuotesLine.contains(StringPool.EQUAL)) {

			addMessage(
				fileName, "There should be a line break after '='", lineNumber);
		}

		if (trimmedLine.matches("^\\} (catch|else|finally) .*")) {
			addMessage(
				fileName, "There should be a line break after '}'", lineNumber);
		}

		Matcher matcher = _incorrectLineBreakPattern6.matcher(trimmedLine);

		if (matcher.find() && (getLevel(matcher.group(4)) > 1)) {
			int x = trimmedLine.indexOf("(", matcher.start(4));

			String linePart = trimmedLine.substring(0, x + 1);

			addMessage(
				fileName,
				"There should be a line break after '" + linePart + "'",
				lineNumber);
		}

		if (trimmedLine.matches("for \\(.*[^;{]")) {
			int x = trimmedLine.length();

			while (true) {
				x = trimmedLine.lastIndexOf(StringPool.SEMICOLON, x - 1);

				if (x == -1) {
					break;
				}

				if (!ToolsUtil.isInsideQuotes(trimmedLine, x)) {
					addMessage(
						fileName,
						"There should be a line break after '" +
							trimmedLine.substring(0, x + 1) + "'",
						lineNumber);
				}
			}
		}
	}

	private String _fixClassOrEnumLineLineBreaks(String content) {
		Matcher matcher = _classOrEnumPattern.matcher(content);

		while (matcher.find()) {
			String indent = matcher.group(2);
			String match = matcher.group(1);

			String inBetweenCurlyBraces = matcher.group(9);

			if (inBetweenCurlyBraces != null) {
				if (Validator.isNull(inBetweenCurlyBraces)) {

					// Case: public class Test {}

					return StringUtil.replaceFirst(
						content, "}", "\n" + indent + "}", matcher.end(9));
				}

				// Case: public enum Test { VALUE1, VALUE2 }

				return StringUtil.replaceFirst(
					content, inBetweenCurlyBraces + "}",
					StringBundler.concat(
						"\n\n\t", indent, StringUtil.trim(inBetweenCurlyBraces),
						"\n\n", indent, "}"),
					matcher.start(8));
			}

			String firstTrailingNonWhitespace = matcher.group(13);

			String trailingWhitespace = matcher.group(12);

			if (!trailingWhitespace.contains("\n") &&
				!firstTrailingNonWhitespace.equals("}")) {

				return StringUtil.replace(content, match, match + "\n");
			}

			String formattedClassLine = _getFormattedClassLine(indent, match);

			if (formattedClassLine != null) {
				content = StringUtil.replace(
					content, match, formattedClassLine);
			}
		}

		return content;
	}

	private String _fixIncorrectLineBreaks(String content, String fileName) {
		Matcher matcher = _incorrectLineBreakPattern1.matcher(content);

		while (matcher.find()) {
			String matchingLine = matcher.group(2);

			if (!matchingLine.startsWith(StringPool.DOUBLE_SLASH) &&
				!matchingLine.startsWith(StringPool.STAR)) {

				return StringUtil.replaceFirst(
					content, matcher.group(3), "\n" + matcher.group(1) + "}\n",
					matcher.start(3) - 1);
			}
		}

		matcher = _incorrectLineBreakPattern2.matcher(content);

		while (matcher.find()) {
			String tabs = matcher.group(2);

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\n", tabs, "([^\t]{2})(?!.*\n", tabs, "[^\t])"),
				Pattern.DOTALL);

			Matcher matcher2 = pattern.matcher(
				content.substring(0, matcher.start(2)));

			if (matcher2.find()) {
				String match = matcher2.group(1);

				if (!match.equals(").")) {
					return StringUtil.replaceFirst(
						content, "\n" + matcher.group(2), StringPool.BLANK,
						matcher.end(1));
				}
			}
		}

		matcher = _incorrectLineBreakPattern3.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(), "{", "}") > 0) {
				return StringUtil.replaceFirst(
					content, "{", "{\n" + matcher.group(1) + "\t",
					matcher.start());
			}
		}

		matcher = _incorrectLineBreakPattern4.matcher(content);

		while (matcher.find()) {
			if (content.charAt(matcher.end()) != CharPool.NEW_LINE) {
				continue;
			}

			String singleLine =
				matcher.group(1) + StringUtil.trimLeading(matcher.group(2)) +
					matcher.group(3);

			if (getLineLength(singleLine) <= getMaxLineLength()) {
				return StringUtil.replace(
					content, matcher.group(), "\n" + singleLine);
			}
		}

		matcher = _incorrectLineBreakPattern7.matcher(content);

		while (matcher.find()) {
			String linePart = matcher.group(2);

			if (getLevel(linePart) != 1) {
				continue;
			}

			if (StringUtil.count(matcher.group(), CharPool.NEW_LINE) > 2) {
				addMessage(
					fileName,
					"For better readability, create new var for the array in " +
						"the 'for' statement",
					getLineNumber(content, matcher.start()));

				continue;
			}

			String match = matcher.group();

			String replacement = StringUtil.replace(match, "\n\t", "\n\t\t");

			replacement = StringUtil.replaceFirst(
				replacement, linePart,
				"\n\t\t" + matcher.group(1) + StringUtil.trim(linePart));

			return StringUtil.replace(content, match, replacement);
		}

		matcher = _incorrectLineBreakPattern5.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group()) == 0) {
				int lineNumber = getLineNumber(content, matcher.start());

				addMessage(
					fileName,
					"There should be a line break before '" + matcher.group(1) +
						"'",
					lineNumber);
			}
		}

		return content;
	}

	private String _fixIncorrectLineBreaksInsideChains(
		String content, String fileName) {

		Matcher matcher = _incorrectLineBreakInsideChainPattern1.matcher(
			content);

		while (matcher.find()) {
			String linePart = matcher.group(2);

			if (linePart.matches("\\)[^\\)]+[\\(;]")) {
				return StringUtil.insert(
					content, "\n" + matcher.group(1), matcher.start(2));
			}
		}

		matcher = _incorrectLineBreakInsideChainPattern2.matcher(content);

		while (matcher.find()) {
			int x = matcher.end();

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

				if (x == -1) {
					return content;
				}

				if (ToolsUtil.isInsideQuotes(content, x)) {
					continue;
				}

				String s = content.substring(matcher.end(), x);

				if (getLevel(s) != 0) {
					continue;
				}

				char c = content.charAt(x - 1);

				if (c == CharPool.TAB) {
					break;
				}

				int y = content.lastIndexOf(StringPool.TAB, x);

				s = content.substring(y + 1, x);

				addMessage(
					fileName, "There should be a line break after '" + s + "'",
					getLineNumber(content, x));

				break;
			}
		}

		matcher = _incorrectLineBreakInsideChainPattern3.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(1)) <= 0) {
				String methodName = matcher.group(2);

				if (!methodName.equals("concat")) {
					addMessage(
						fileName,
						"Chaining on method '" + methodName +
							"' is allowed, but incorrect styling",
						"chaining.markdown",
						getLineNumber(content, matcher.end(1)));
				}
			}
		}

		matcher = _incorrectLineBreakInsideChainPattern4.matcher(content);

		while (matcher.find()) {
			String s = matcher.group(2);

			if (!s.matches("\\)+[,;]?")) {
				addMessage(
					fileName,
					"There should be a line break after '" + matcher.group(1) +
						"'",
					getLineNumber(content, matcher.start()));
			}
		}

		return content;
	}

	private String _fixLineStartingWithCloseParenthesis(
			String content, String fileName)
		throws CheckstyleException {

		Matcher matcher = _lineStartingWithCloseParenthesisPattern.matcher(
			content);

		while (matcher.find()) {
			String lastCharacterPreviousLine = matcher.group(1);

			if (lastCharacterPreviousLine.equals(StringPool.OPEN_PARENTHESIS)) {
				addMessage(
					fileName, "Line should not start with ')'",
					getLineNumber(content, matcher.start(1)));

				return content;
			}

			int x = matcher.end(2) + 1;

			int y = x - 1;

			while (true) {
				if (ToolsUtil.isInsideQuotes(content, y) ||
					(getLevel(content.substring(y, x)) != 0)) {

					y--;

					continue;
				}

				String trimmedLine = StringUtil.trimLeading(
					getLine(content, getLineNumber(content, y)));

				if (trimmedLine.startsWith(").") ||
					trimmedLine.startsWith("@") ||
					_hasAllowedChain(trimmedLine)) {

					break;
				}

				return StringUtil.replaceFirst(
					content, "\n" + matcher.group(2), StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private String _fixMultiLineComment(String content) {
		Matcher matcher = _incorrectMultiLineCommentPattern.matcher(content);

		return matcher.replaceAll("$1$2$3");
	}

	private String _getFormattedClassLine(String indent, String classLine) {
		while (classLine.contains(StringPool.TAB + StringPool.SPACE)) {
			classLine = StringUtil.replace(
				classLine, StringPool.TAB + StringPool.SPACE, StringPool.TAB);
		}

		String classSingleLine = StringUtil.replace(
			classLine.substring(1),
			new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		classSingleLine = indent + classSingleLine;

		List<String> lines = new ArrayList<>();

		outerWhile:
		while (true) {
			if (getLineLength(classSingleLine) <= getMaxLineLength()) {
				lines.add(classSingleLine);

				break;
			}

			String newIndent = indent;
			String newLine = classSingleLine;

			int x = -1;

			while (true) {
				int y = newLine.indexOf(" extends ", x + 1);

				if (y == -1) {
					x = newLine.indexOf(" implements ", x + 1);
				}
				else {
					x = y;
				}

				if (x == -1) {
					break;
				}

				String linePart = newLine.substring(0, x);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= getMaxLineLength())) {

					if (lines.isEmpty()) {
						newIndent = newIndent + StringPool.TAB;
					}

					lines.add(linePart);

					newLine = newIndent + newLine.substring(x + 1);

					if (getLineLength(newLine) <= getMaxLineLength()) {
						lines.add(newLine);

						break outerWhile;
					}

					x = -1;
				}
			}

			if (lines.isEmpty()) {
				return null;
			}

			x = newLine.length();

			while (true) {
				x = newLine.lastIndexOf(", ", x - 1);

				if (x == -1) {
					Matcher matcher = _incorrectExtendImplementPattern1.matcher(
						classLine);

					if (matcher.find()) {
						String replacement = StringBundler.concat(
							"\n", matcher.group(2), matcher.group(1),
							StringPool.SPACE);

						return StringUtil.replaceFirst(
							classLine, matcher.group(), replacement);
					}

					matcher = _incorrectExtendImplementPattern2.matcher(
						classLine);

					if (matcher.find()) {
						String replacement = StringBundler.concat(
							matcher.group(2), matcher.group(3),
							StringPool.SPACE, matcher.group(4));

						if (getLineLength(replacement) <= getMaxLineLength()) {
							return StringUtil.replaceFirst(
								classLine, matcher.group(1), replacement);
						}
					}

					return null;
				}

				String linePart = newLine.substring(0, x + 1);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= getMaxLineLength())) {

					lines.add(linePart);

					if (linePart.contains("\textends")) {
						newIndent = newIndent + "\t\t";
					}
					else if (linePart.contains("\timplements")) {
						newIndent = newIndent + "\t\t   ";
					}

					newLine = newIndent + newLine.substring(x + 2);

					if (getLineLength(newLine) <= getMaxLineLength()) {
						lines.add(newLine);

						break outerWhile;
					}

					x = newLine.length();
				}
			}
		}

		String formattedClassLine = null;

		for (String line : lines) {
			if (formattedClassLine == null) {
				formattedClassLine = "\n" + line;
			}
			else {
				formattedClassLine = formattedClassLine + "\n" + line;
			}
		}

		return formattedClassLine;
	}

	private boolean _hasAllowedChain(String line) throws CheckstyleException {
		Map<String, String> checkstyleAttributesMap =
			getCheckstyleAttributesMap("ChainingCheck");

		String allowedClassNames = GetterUtil.getString(
			checkstyleAttributesMap.get("allowedClassNames"));
		String allowedVariableTypeNames = GetterUtil.getString(
			checkstyleAttributesMap.get("allowedVariableTypeNames"));

		String[] values = ArrayUtil.append(
			StringUtil.split(allowedClassNames),
			StringUtil.split(allowedVariableTypeNames));

		for (String value : values) {
			if (line.matches(".*" + value + "\\..*")) {
				return true;
			}
		}

		String allowedMethodNames = GetterUtil.getString(
			checkstyleAttributesMap.get("allowedMethodNames"));

		values = StringUtil.split(allowedMethodNames);

		for (String value : values) {
			if (line.matches(".*" + value + "\\(.*")) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _classOrEnumPattern = Pattern.compile(
		"(\n(\t*)(private|protected|public) ((abstract|final|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?)\\{)((.*)\\})?" +
				"([ \t]*(\\Z|\n)(\\s*)(\\S))");
	private static final Pattern _incorrectExtendImplementPattern1 =
		Pattern.compile(" (extends|implements)\n(\t+)");
	private static final Pattern _incorrectExtendImplementPattern2 =
		Pattern.compile("\n((\t+)(extends|implements)\n\t+(\\w+))\n");
	private static final Pattern _incorrectLineBreakInsideChainPattern1 =
		Pattern.compile("\n(\t*)\\).*?\\((.+)");
	private static final Pattern _incorrectLineBreakInsideChainPattern2 =
		Pattern.compile("\t\\)\\..*\\(\n");
	private static final Pattern _incorrectLineBreakInsideChainPattern3 =
		Pattern.compile("\n(.*\\S)\\)\\.(.*)\\(\n");
	private static final Pattern _incorrectLineBreakInsideChainPattern4 =
		Pattern.compile("\t(\\)\\.[^\\)\\(]+\\()(.+)\n");
	private static final Pattern _incorrectLineBreakPattern1 = Pattern.compile(
		"\n(\t*)(.*\\) \\{)([\t ]*\\}\n)");
	private static final Pattern _incorrectLineBreakPattern2 = Pattern.compile(
		"\n(\t*).*\\}\n(\t*)\\);");
	private static final Pattern _incorrectLineBreakPattern3 = Pattern.compile(
		"\n(\t*)\\{.+(?<!\\}\\){0,10}(,|;)?)\n");
	private static final Pattern _incorrectLineBreakPattern4 = Pattern.compile(
		"\n(\t+\\{)\n(.*[^;])\n\t+(\\},?)");
	private static final Pattern _incorrectLineBreakPattern5 = Pattern.compile(
		", (new .*\\(.*\\) \\{)\n");
	private static final Pattern _incorrectLineBreakPattern6 = Pattern.compile(
		"^(((else )?if|for|try|while) \\()?\\(*(.*\\()$");
	private static final Pattern _incorrectLineBreakPattern7 = Pattern.compile(
		"(\t+)for \\(.*:(.+\\()\n[\\s\\S]+?\\) \\{\n");
	private static final Pattern _incorrectMultiLineCommentPattern =
		Pattern.compile("(\n\t*/\\*)\n\t*(.*?)\n\t*(\\*/\n)", Pattern.DOTALL);
	private static final Pattern _lineStartingWithCloseParenthesisPattern =
		Pattern.compile("(.)\n+(\t+)\\)[^.].*\n");

}