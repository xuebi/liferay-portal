<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
int contentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);
%>

<h3><liferay-ui:message key="content-sharing" /></h3>

<aui:fieldset>
	<aui:select label="allow-subsites-to-display-content-from-parent-sites" name='<%= "settings--" + PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED + "--" %>'>
		<aui:option label="enabled-by-default" selected="<%= contentSharingWithChildrenEnabled == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT %>" />
		<aui:option label="disabled-by-default" selected="<%= contentSharingWithChildrenEnabled == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED_BY_DEFAULT %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED_BY_DEFAULT %>" />
		<aui:option label="disabled" selected="<%= contentSharingWithChildrenEnabled == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED %>" />
	</aui:select>
</aui:fieldset>