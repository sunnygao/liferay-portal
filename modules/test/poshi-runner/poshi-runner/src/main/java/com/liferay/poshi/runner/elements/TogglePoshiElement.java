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

package com.liferay.poshi.runner.elements;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class TogglePoshiElement extends BasePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new TogglePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new TogglePoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith("toggle (")) {
				String parentheticalContent = getParentheticalContent(
						readableBlock);

				String summary = getQuotedContent(parentheticalContent);

				addAttribute("name", summary);

				continue;
			}

			add(PoshiElementFactory.newPoshiElement(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n");

		sb.append(getPad());
		sb.append(getBlockName());

		sb.append(" (\"");
		sb.append(attributeValue("name"));
		sb.append("\") {");

		PoshiElement onPoshiElement = (PoshiElement)element("on");

		sb.append(onPoshiElement.toReadableSyntax());

		PoshiElement offPoshiElement = (PoshiElement)element("off");

		sb.append(offPoshiElement.toReadableSyntax());

		sb.append("\n");
		sb.append(getPad());
		sb.append("}");

		return sb.toString();
	}

	protected String getReadableName() {
		return getName();
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (line.startsWith(getReadableName() + " (") && line.endsWith("{") &&
					(readableBlock.length() == 0)) {

				readableBlocks.add(line);

				continue;
			}

			if (line.endsWith("{") && readableBlocks.isEmpty()) {
				continue;
			}

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	@Override
	protected String getBlockName() {
		return "toggle";
	}

	protected TogglePoshiElement() {
	}

	protected TogglePoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected TogglePoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	private boolean _isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.startsWith("toggle (")) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "toggle";

}