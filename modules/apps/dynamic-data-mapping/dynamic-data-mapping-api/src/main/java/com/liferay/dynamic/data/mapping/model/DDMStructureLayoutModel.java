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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;

/**
 * The base model interface for the DDMStructureLayout service. Represents a row in the &quot;DDMStructureLayout&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayout
 * @generated
 */
@ProviderType
public interface DDMStructureLayoutModel extends BaseModel<DDMStructureLayout>,
	GroupedModel, ShardedModel, StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a ddm structure layout model instance should use the {@link DDMStructureLayout} interface instead.
	 */

	/**
	 * Returns the primary key of this ddm structure layout.
	 *
	 * @return the primary key of this ddm structure layout
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this ddm structure layout.
	 *
	 * @param primaryKey the primary key of this ddm structure layout
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this ddm structure layout.
	 *
	 * @return the uuid of this ddm structure layout
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this ddm structure layout.
	 *
	 * @param uuid the uuid of this ddm structure layout
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the structure layout ID of this ddm structure layout.
	 *
	 * @return the structure layout ID of this ddm structure layout
	 */
	public long getStructureLayoutId();

	/**
	 * Sets the structure layout ID of this ddm structure layout.
	 *
	 * @param structureLayoutId the structure layout ID of this ddm structure layout
	 */
	public void setStructureLayoutId(long structureLayoutId);

	/**
	 * Returns the group ID of this ddm structure layout.
	 *
	 * @return the group ID of this ddm structure layout
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this ddm structure layout.
	 *
	 * @param groupId the group ID of this ddm structure layout
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this ddm structure layout.
	 *
	 * @return the company ID of this ddm structure layout
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this ddm structure layout.
	 *
	 * @param companyId the company ID of this ddm structure layout
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this ddm structure layout.
	 *
	 * @return the user ID of this ddm structure layout
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this ddm structure layout.
	 *
	 * @param userId the user ID of this ddm structure layout
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this ddm structure layout.
	 *
	 * @return the user uuid of this ddm structure layout
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this ddm structure layout.
	 *
	 * @param userUuid the user uuid of this ddm structure layout
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this ddm structure layout.
	 *
	 * @return the user name of this ddm structure layout
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this ddm structure layout.
	 *
	 * @param userName the user name of this ddm structure layout
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this ddm structure layout.
	 *
	 * @return the create date of this ddm structure layout
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this ddm structure layout.
	 *
	 * @param createDate the create date of this ddm structure layout
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this ddm structure layout.
	 *
	 * @return the modified date of this ddm structure layout
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this ddm structure layout.
	 *
	 * @param modifiedDate the modified date of this ddm structure layout
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the structure version ID of this ddm structure layout.
	 *
	 * @return the structure version ID of this ddm structure layout
	 */
	public long getStructureVersionId();

	/**
	 * Sets the structure version ID of this ddm structure layout.
	 *
	 * @param structureVersionId the structure version ID of this ddm structure layout
	 */
	public void setStructureVersionId(long structureVersionId);

	/**
	 * Returns the definition of this ddm structure layout.
	 *
	 * @return the definition of this ddm structure layout
	 */
	@AutoEscape
	public String getDefinition();

	/**
	 * Sets the definition of this ddm structure layout.
	 *
	 * @param definition the definition of this ddm structure layout
	 */
	public void setDefinition(String definition);
}