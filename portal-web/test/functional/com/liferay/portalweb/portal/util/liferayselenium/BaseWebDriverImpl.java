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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverToSeleniumBridge implements LiferaySelenium {

	public BaseWebDriverImpl(
		String projectDir, String browserURL, WebDriver webDriver) {

		super(webDriver);

		_projectDir = projectDir;

		webDriver.get(browserURL);
	}

	public void assertAlert(String pattern) {
		LiferaySeleniumHelper.assertAlert(this, pattern);
	}

	public void assertChecked(String locator) {
		LiferaySeleniumHelper.assertChecked(this, locator);
	}

	public void assertConfirmation(String pattern) {
		LiferaySeleniumHelper.assertConfirmation(this, pattern);
	}

	public void assertElementNotPresent(String locator) {
		LiferaySeleniumHelper.assertElementNotPresent(this, locator);
	}

	public void assertElementPresent(String locator) {
		LiferaySeleniumHelper.assertElementPresent(this, locator);
	}

	public void assertLocation(String pattern) {
		LiferaySeleniumHelper.assertLocation(this, pattern);
	}

	public void assertNotAlert(String pattern) {
		LiferaySeleniumHelper.assertNotAlert(this, pattern);
	}

	public void assertNotChecked(String locator) {
		LiferaySeleniumHelper.assertNotChecked(this, locator);
	}

	public void assertNotLocation(String pattern) {
		LiferaySeleniumHelper.assertNotLocation(this, pattern);
	}

	public void assertNotPartialText(String locator, String pattern) {
		LiferaySeleniumHelper.assertNotPartialText(this, locator, pattern);
	}

	public void assertNotSelectedLabel(String selectLocator, String pattern) {
		LiferaySeleniumHelper.assertNotSelectedLabel(
			this, selectLocator, pattern);
	}

	public void assertNotText(String locator, String pattern) {
		LiferaySeleniumHelper.assertNotText(this, locator, pattern);
	}

	public void assertNotValue(String locator, String pattern) {
		LiferaySeleniumHelper.assertNotValue(this, locator, pattern);
	}

	public void assertNotVisible(String locator) {
		LiferaySeleniumHelper.assertNotVisible(this, locator);
	}

	public void assertPartialText(String locator, String pattern) {
		LiferaySeleniumHelper.assertPartialText(this, locator, pattern);
	}

	public void assertSelectedLabel(String selectLocator, String pattern) {
		LiferaySeleniumHelper.assertSelectedLabel(this, selectLocator, pattern);
	}

	public void assertText(String locator, String pattern) {
		LiferaySeleniumHelper.assertText(this, locator, pattern);
	}

	public void assertTextNotPresent(String pattern) {
		LiferaySeleniumHelper.assertTextNotPresent(this, pattern);
	}

	public void assertTextPresent(String pattern) {
		LiferaySeleniumHelper.assertTextPresent(this, pattern);
	}

	public void assertValue(String locator, String pattern) {
		LiferaySeleniumHelper.assertValue(this, locator, pattern);
	}

	public void assertVisible(String locator) {
		LiferaySeleniumHelper.assertVisible(this, locator);
	}

	public void clickAndWait(String locator) {
		super.click(locator);
		super.waitForPageToLoad("30000");
	}

	public void clickAtAndWait(String locator, String coordString) {
		super.clickAt(locator, coordString);
		super.waitForPageToLoad("30000");
	}

	public void copyText(String locator) {
		_clipBoard = super.getText(locator);
	}

	public void copyValue(String locator) {
		_clipBoard = super.getValue(locator);
	}

	public void downloadTempFile(String value) {
		LiferaySeleniumHelper.downloadTempFile(value);
	}

	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	public String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	public String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
	}

	public String getFirstNumber(String locator) {
		WebElement webElement = getWebElement(locator);

		String text = webElement.getText();

		if (text == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean digit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);

				digit = true;
			}

			String s = sb.toString();

			if (Validator.isNotNull(s) && !digit) {
				return s;
			}
		}

		return sb.toString();
	}

	public String getFirstNumberIncrement(String locator) {
		String firstNumber = getFirstNumber(locator);

		return StringUtil.valueOf(GetterUtil.getInteger(firstNumber) + 1);
	}

	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	public void goBackAndWait() {
		super.goBack();
		super.waitForPageToLoad("30000");
	}

	public boolean isElementNotPresent(String locator) {
		return LiferaySeleniumHelper.isElementNotPresent(this, locator);
	}

	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	public boolean isNotPartialText(String locator, String value) {
		return LiferaySeleniumHelper.isNotPartialText(this, locator, value);
	}

	public boolean isNotText(String locator, String value) {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	public boolean isPartialText(String locator, String value) {
		WebElement webElement = getWebElement(locator, "1");

		String text = webElement.getText();

		return text.contains(value);
	}

	public boolean isText(String locator, String value) {
		return LiferaySeleniumHelper.isText(this, locator, value);
	}

	public void keyDownAndWait(String locator, String keySequence) {
		super.keyDown(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void keyPressAndWait(String locator, String keySequence) {
		super.keyPress(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void keyUpAndWait(String locator, String keySequence) {
		super.keyUp(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void paste(String location) {
		super.type(location, _clipBoard);
	}

	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	public void refreshAndWait() {
		super.refresh();
		super.waitForPageToLoad("30000");
	}

	public void saveScreenShotAndSource() throws Exception {
	}

	public void selectAndWait(String selectLocator, String optionLocator) {
		super.select(selectLocator, optionLocator);
		super.waitForPageToLoad("30000");
	}

	public void sendKeys(String locator, String value) {
		typeKeys(locator, value);
	}

	public void setBrowserOption() {
		LiferaySeleniumHelper.setBrowserOption();
	}

	public void setDefaultTimeout() {
	}

	public void uploadCommonFile(String location, String value) {
		uploadFile(
			location,
			_projectDir + "portal-web\\test\\functional\\com\\liferay\\" +
				"portalweb\\dependencies\\" + value);
	}

	public void uploadFile(String location, String value) {
		WebElement webElement = getWebElement(location);

		webElement.sendKeys(value);
	}

	public void uploadTempFile(String location, String value) {
		uploadFile(location, TestPropsValues.OUTPUT_DIR + value);
	}

	public void waitForConfirmation(String pattern) throws Exception {
		int timeout =
			TestPropsValues.TIMEOUT_EXPLICIT_WAIT /
				TestPropsValues.TIMEOUT_IMPLICIT_WAIT;

		for (int second = 0;; second++) {
			if (second >= timeout) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (pattern.equals(getConfirmation())) {
					break;
				}
			}
			catch (Exception e) {
			}
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementNotPresent(this, locator);
	}

	public void waitForElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementPresent(this, locator);
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForNotPartialText(this, locator, value);
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForNotSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForNotText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotText(this, locator, value);
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotValue(this, locator, value);
	}

	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	public void waitForTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextNotPresent(this, value);
	}

	public void waitForTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextPresent(this, value);
	}

	public void waitForValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForValue(this, locator, value);
	}

	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	public void windowMaximizeAndWait() {
		super.windowMaximize();
		super.waitForPageToLoad("30000");
	}

	private String _clipBoard = "";
	private String _projectDir;

}