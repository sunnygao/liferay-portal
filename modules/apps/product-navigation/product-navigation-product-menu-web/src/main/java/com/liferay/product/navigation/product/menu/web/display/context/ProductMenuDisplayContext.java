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

package com.liferay.product.navigation.product.menu.web.display.context;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Julio Camarero
 */
public class ProductMenuDisplayContext {

	public ProductMenuDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_panelAppRegistry = (PanelAppRegistry)_portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY);

		_panelCategoryRegistry =
			(PanelCategoryRegistry)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
	}

	public List<PanelCategory> getChildPanelCategories() {
		if (_childPanelCategories != null) {
			return _childPanelCategories;
		}

		_childPanelCategories = _panelCategoryRegistry.getChildPanelCategories(
			PanelCategoryKeys.ROOT, _themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroup());

		return _childPanelCategories;
	}

	public String getRootPanelCategoryKey() {
		if (_rootPanelCategoryKey != null) {
			return _rootPanelCategoryKey;
		}

		_rootPanelCategoryKey = StringPool.BLANK;

		List<PanelCategory> childPanelCategories = getChildPanelCategories();

		if (!childPanelCategories.isEmpty()) {
			PanelCategory firstChildPanelCategory = childPanelCategories.get(0);

			_rootPanelCategoryKey = firstChildPanelCategory.getKey();

			if (Validator.isNotNull(_themeDisplay.getPpid())) {
				PanelCategoryHelper panelCategoryHelper =
					new PanelCategoryHelper(
						_panelAppRegistry, _panelCategoryRegistry);

				for (PanelCategory panelCategory :
						_panelCategoryRegistry.getChildPanelCategories(
							PanelCategoryKeys.ROOT)) {

					if (panelCategoryHelper.containsPortlet(
							_themeDisplay.getPpid(), panelCategory)) {

						_rootPanelCategoryKey = panelCategory.getKey();

						return _rootPanelCategoryKey;
					}
				}
			}
		}

		return _rootPanelCategoryKey;
	}

	public boolean showProductMenu() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return true;
		}

		List<PanelCategory> childPanelCategories = getChildPanelCategories();

		// If only the Personal Panel is shown, then the product menu itself
		// will not be shown to users

		if (childPanelCategories.size() <= 1) {
			return false;
		}

		return true;
	}

	private List<PanelCategory> _childPanelCategories;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private String _rootPanelCategoryKey;
	private final ThemeDisplay _themeDisplay;

}