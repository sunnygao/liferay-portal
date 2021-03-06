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

package com.liferay.message.boards.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalServiceUtil;

/**
 * The extended model base implementation for the MBBan service. Represents a row in the &quot;MBBan&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBBanImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBanImpl
 * @see MBBan
 * @generated
 */
@ProviderType
public abstract class MBBanBaseImpl extends MBBanModelImpl implements MBBan {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards ban model instance should use the <code>MBBan</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			MBBanLocalServiceUtil.addMBBan(this);
		}
		else {
			MBBanLocalServiceUtil.updateMBBan(this);
		}
	}
}