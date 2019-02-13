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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import javax.annotation.Generated;

import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseUserAccountResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			_url.toExternalForm() + "/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteUserAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetMyUserAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetOrganizationUserAccountsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetUserAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetUserAccountsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetWebSiteUserAccountsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostUserAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostUserAccountBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutUserAccount() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteUserAccount(Long userAccountId)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts/{user-account-id}");
	}

	protected void invokeGetMyUserAccount(Long myUserAccountId)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/my-user-accounts/{my-user-account-id}");
	}

	protected void invokeGetOrganizationUserAccountsPage(
			Long organizationId, Pagination pagination)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post(
				"/organizations/{organization-id}/user-accounts");
	}

	protected void invokeGetUserAccount(Long userAccountId) throws Exception {
			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts/{user-account-id}");
	}

	protected void invokeGetUserAccountsPage(
			String fullnamequery, Pagination pagination)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts");
	}

	protected void invokeGetWebSiteUserAccountsPage(
			Long webSiteId, Pagination pagination)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/web-sites/{web-site-id}/user-accounts");
	}

	protected void invokePostUserAccount(UserAccount userAccount)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts");
	}

	protected void invokePostUserAccountBatchCreate(UserAccount userAccount)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts/batch-create");
	}

	protected void invokePutUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

			RequestSpecification requestSpecification =
				_createRequestRequestSpecification();

			requestSpecification.post("/user-accounts/{user-account-id}");
	}

	protected UserAccount randomUserAccount() {
		UserAccount userAccount = new UserAccount();

		return userAccount;
	}

	protected Group testGroup;

	private RequestSpecification _createRequestRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

	@ArquillianResource
	private URL _url;

}