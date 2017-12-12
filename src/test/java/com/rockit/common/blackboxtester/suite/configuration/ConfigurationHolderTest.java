package com.rockit.common.blackboxtester.suite.configuration;

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

}
