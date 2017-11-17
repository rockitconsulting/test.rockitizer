[Back to Main](../README.md)


## Build test.rockitizer
In order to build the runtime please execute the following steps: 
1. Import the project as maven project using your ide (e.g. Eclipse IDE)
2. Provide the non-free dependencies upon your connector needs, i.e. mq libs, oracle client, db2, etc. (see pom.xml below <!-- Non-free libs start  -->) 
3. Run maven build 


## Non-free libs

```xml
	<!-- Non-free MQ start -->
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>mq</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>connector</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>jmqi</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>commonservices</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>headers</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.mq</groupId>
			<artifactId>pcf</artifactId>
		</dependency>
	<!-- Non-free MQ end -->	
	
	<!-- Non-free DB start -->	
		<dependency>
			<groupId>com.oracle</groupId>
			<artifactId>ojdbc6</artifactId>
		</dependency>
		<dependency>
			<groupId>com.ibm.db2</groupId>
			<artifactId>db2jcc4</artifactId>
		</dependency>
	<!-- Non-free MQ end -->	
```





