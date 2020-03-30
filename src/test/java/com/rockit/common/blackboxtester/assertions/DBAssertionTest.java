package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class DBAssertionTest {
	public static Logger LOGGER = Logger.getLogger(DBAssertionTest.class.getName());
	DBAssertion DBAssertion;

	/**
	 * DB assertion with 2 sample parameter values: sqlQuery, mustContainTokens.
	 * @throws IOException
	 */
	@Test
	public void testToStringTwoParms() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion("SELECT * FROM ROCKIT.BOOKSERVICE;", ImmutableList.of("createdAt", "id"));
		assertNotNull(TestDBAssertion.toString());
	}

	/**
	 * DB assertion with 2 null parameter values: sqlQuery, mustContainTokens.
	 * @throws IOException
	 */

	@Test
	public void testToStringTwoNullParms() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion(null, null);
		assertNotNull(TestDBAssertion.toString());
	}
	
	/**
	 * DB assertion with 2 empty parameter values: sqlQuery,
	 * mustContainTokens.
	 * @throws IOException
	 */

	@Test
	public void testWithTwoEmptyParms() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion("", ImmutableList.of(""));
		assertNotNull(TestDBAssertion.toString());
	}		

	/**
	 * DB assertion with 3 sample parameter values: datasourceId, sqlQuery,
	 * mustContainTokens.
	 * @throws IOException
	 */

	@Test
	public void testWithThreeParms() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion("defaultDB", "SELECT * FROM ROCKIT.BOOKSERVICE;", ImmutableList.of("createdAt", "id"));
		assertNotNull(TestDBAssertion.toString());
	}

	/**
	 * DB assertion with 3 null parameter values: datasourceId, sqlQuery,
	 * mustContainTokens.
	 * @throws IOException
	 */

	@Test
	public void testWithThreeNullParmsListIsNull() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion(null, null, null);
		assertNotNull(TestDBAssertion.toString());
	}
	
	/**
	 * DB assertion with 3 empty parameter values: datasourceId, sqlQuery,
	 * mustContainTokens.
	 * @throws IOException
	 */

	@Test
	public void testWithThreeEmptyParms() throws IOException {
		DBAssertion TestDBAssertion = new DBAssertion("", "", ImmutableList.of("", "", ""));
		assertNotNull(TestDBAssertion.toString());
	}
	
}
