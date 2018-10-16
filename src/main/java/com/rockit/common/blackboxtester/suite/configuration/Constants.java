package com.rockit.common.blackboxtester.suite.configuration;

public final class Constants {

	public static final int SCP_PORT = 22;
	public static final int SCP_FILE_MODE = 0777;
	public static final String BEFORE_FOLDER = "0BEFORE";
	public static final String OUTPUT_FOLDER = "output";
	public static final String DEFAULT_SLEEP_MS = "20000";
	public static final String FLOW_NAME_SEPARATOR = "#";

	public static final String DEFAULT_CONNECTOR_NAME = "default#";
	public static final String DEFAULT_MQPUT = "default#MQPUT.";
	public static final String DEFAULT_MQGET = "default#MQGET.";

	public static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

	
	public static final String MQMANAGER_NAME_KEY    = "MQMANAGER.NAME";
	public static final String MQMANAGER_PORT_KEY    = "MQMANAGER.PORT";
	public static final String MQMANAGER_CHANNEL_KEY = "MQMANAGER.CHANNEL";
	public static final String MQMANAGER_HOST_KEY    = "MQMANAGER.HOST";
	public static final String MQMANAGER_USR_KEY     = "MQMANAGER.USR";
	public static final String MQMANAGER_PWD_KEY     = "MQMANAGER.PWD";//NOSONAR
	
	public static final String DATASOURCE_URL_KEY    =   "DATASOURCE.URL";
	public static final String DATASOURCE_USERNAME_KEY = "DATASOURCE.USERNAME";
	public static final String DATASOURCE_PASSWORD_KEY = "DATASOURCE.PASSWORD";//NOSONAR
	
	public static final String URL = 			        "URL";
	public static final String METHOD = 			    "METHOD";
	public static final String CONTENTTYPE = 			"CONTENTTYPE";

	public static final String SCP_HOST_KEY = 			".HOST";
	public static final String SCP_USR_KEY = 			".USR";
	public static final String SCP_PWD_KEY = 			".PWD";//NOSONAR
	public static final String SCP_DEST_PATH_KEY = 		".PATH";
	
	public static final String MQOUT_KEY = "MQGET.";
	public static final String FILE_PATH_KEY = ".PATH";
	
	public static final String REPLAY_PATH = "/target/replay/";
	public static final String RECORD_PATH = "/src/test/resources/";
	public static final String MODE_KEY = "SUITE.MODE";


	public static  enum Connectors {
		MQGET("MQGET."), MQPUT("MQPUT."), 
		SCPPUT("SCPPUT."), SCPGET("SCPGET."),
		FILEPUT("FILEPUT."),FILEGET("FILEGET."),FILEDEL("FILEDEL."),
		DBPUT("DBPUT."), DBGET("DBGET."), 
		HTTPGET("HTTPGET."),
		HTTP("HTTP.");
	

		private final String value;

		private Connectors(final String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}
