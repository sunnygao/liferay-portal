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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for WebDAVProps. This utility wraps
 * <code>com.liferay.portal.service.impl.WebDAVPropsLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsLocalService
 * @generated
 */
@ProviderType
public class WebDAVPropsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.WebDAVPropsLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the web dav props to the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web dav props
	* @return the web dav props that was added
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.kernel.model.WebDAVProps webDAVProps) {
		return getService().addWebDAVProps(webDAVProps);
	}

	/**
	* Creates a new web dav props with the primary key. Does not add the web dav props to the database.
	*
	* @param webDavPropsId the primary key for the new web dav props
	* @return the new web dav props
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps createWebDAVProps(
		long webDavPropsId) {
		return getService().createWebDAVProps(webDavPropsId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the web dav props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web dav props
	* @return the web dav props that was removed
	* @throws PortalException if a web dav props with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps deleteWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWebDAVProps(webDavPropsId);
	}

	public static void deleteWebDAVProps(String className, long classPK) {
		getService().deleteWebDAVProps(className, classPK);
	}

	/**
	* Deletes the web dav props from the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web dav props
	* @return the web dav props that was removed
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps deleteWebDAVProps(
		com.liferay.portal.kernel.model.WebDAVProps webDAVProps) {
		return getService().deleteWebDAVProps(webDAVProps);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebDAVPropsModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebDAVPropsModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.WebDAVProps fetchWebDAVProps(
		long webDavPropsId) {
		return getService().fetchWebDAVProps(webDavPropsId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the web dav props with the primary key.
	*
	* @param webDavPropsId the primary key of the web dav props
	* @return the web dav props
	* @throws PortalException if a web dav props with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps getWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWebDAVProps(webDavPropsId);
	}

	public static com.liferay.portal.kernel.model.WebDAVProps getWebDAVProps(
		long companyId, String className, long classPK) {
		return getService().getWebDAVProps(companyId, className, classPK);
	}

	/**
	* Returns a range of all the web dav propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebDAVPropsModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web dav propses
	* @param end the upper bound of the range of web dav propses (not inclusive)
	* @return the range of web dav propses
	*/
	public static java.util.List<com.liferay.portal.kernel.model.WebDAVProps> getWebDAVPropses(
		int start, int end) {
		return getService().getWebDAVPropses(start, end);
	}

	/**
	* Returns the number of web dav propses.
	*
	* @return the number of web dav propses
	*/
	public static int getWebDAVPropsesCount() {
		return getService().getWebDAVPropsesCount();
	}

	public static void storeWebDAVProps(
		com.liferay.portal.kernel.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().storeWebDAVProps(webDAVProps);
	}

	/**
	* Updates the web dav props in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web dav props
	* @return the web dav props that was updated
	*/
	public static com.liferay.portal.kernel.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.kernel.model.WebDAVProps webDAVProps) {
		return getService().updateWebDAVProps(webDAVProps);
	}

	public static WebDAVPropsLocalService getService() {
		if (_service == null) {
			_service = (WebDAVPropsLocalService)PortalBeanLocatorUtil.locate(WebDAVPropsLocalService.class.getName());

			ReferenceRegistry.registerReference(WebDAVPropsLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static WebDAVPropsLocalService _service;
}