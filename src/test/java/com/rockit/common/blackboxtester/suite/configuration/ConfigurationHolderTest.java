package com.rockit.common.blackboxtester.suite.configuration;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ConfigurationHolderTest {

	@Test
	public void testConfigEmptyValues() {
		assertTrue(ConfigurationHolder.configuration().getString(Constants.MQMANAGER_USR_KEY),
				ConfigurationHolder.configuration().getString(Constants.MQMANAGER_USR_KEY).isEmpty());
	}

	@Test
	public void testGeneratedMqGetNonEmpty() {

		assertTrue(ConfigurationHolder.configuration().getString(Constants.ENV_IDX_KEY),
				ConfigurationHolder.configuration().getString(Constants.ENV_IDX_KEY).equals("YD1"));

		assertTrue(ConfigurationHolder.configuration().getString(Constants.MQOUT_KEY),
				ConfigurationHolder.configuration().getList(Constants.MQOUT_KEY)
						.equals(ImmutableList.of("SPLITCUSTOMER.INOUT.@ENV@", "CUSTOMERCONTRACT.INOUT.@ENV@",
								"CUSTOMERCONTRACT.ERROR.@ENV@", "SINGLE.CUSTOMER.OUT.@ENV@")));
	}

	@Test
	public void testCustomMqGetnonEmpty() {

		final String CUSTOM_KEY = "MQGET.TEST.LIST";
		assertTrue(ConfigurationHolder.configuration().getString(CUSTOM_KEY),
				ConfigurationHolder.configuration().getList(CUSTOM_KEY)
						.equals(ImmutableList.of("SPLITCUSTOMER.INOUT.@ENV@", "CUSTOMERCONTRACT.INOUT.@ENV@",
								"CUSTOMERCONTRACT.ERROR.@ENV@", "SINGLE.CUSTOMER.OUT.@ENV@")));
	}

	@Test
	public void testCustomMqGetEmpty() {

		final String CUSTOM_KEY_EMPTY = "MQGET.TEST.EMPTY.LIST";
		assertTrue(ConfigurationHolder.configuration().getString(CUSTOM_KEY_EMPTY),
				ConfigurationHolder.configuration().getList(CUSTOM_KEY_EMPTY).equals(ImmutableList.of("")));

	}

	@Test(expected = Exception.class)
	public void testCustomMqGetNotExist() {
		final String CUSTOM_KEY_NOT_EXISTS = "MQGET.NOT.EXISTS";
		ConfigurationHolder.configuration().getList(CUSTOM_KEY_NOT_EXISTS);
	}

	@Test
	public void testGetCustomDbConnector() {
		final String CONNECTOR_NAME = "DBGET@MYSELECT";
		assertEquals(
				ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_URL_KEY),
				"jdbc:oracle:thin:@()");
		assertEquals(ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_USERNAME_KEY),
				"USR");
		assertEquals(ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_PASSWORD_KEY),
				"PWD");

	}

	@Test
	public void testGetCustomDbConnectorWithFallback() {
		final String CONNECTOR_NAME = "DBGET@MYSELECTWITHFALLBACK";
		assertEquals(
				ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_URL_KEY),
				"jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(dummy) )");
		assertEquals(ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_USERNAME_KEY),
				"APP");
		assertEquals(ConfigurationHolder.configuration().getPrefixedString(CONNECTOR_NAME, Constants.DATASOURCE_PASSWORD_KEY),
				"APP02");
	}

	
}
