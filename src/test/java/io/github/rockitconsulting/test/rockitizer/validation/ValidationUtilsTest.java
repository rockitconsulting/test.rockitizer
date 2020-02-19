package io.github.rockitconsulting.test.rockitizer.validation;

import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.validation.model.ValidationHolder;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

public class ValidationUtilsTest {
	public static Logger log = Logger.getLogger(ValidationUtilsTest.class.getName());

	

	
	@Before
	public void before () {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationHolder.reset();
	}
	
	@Test
	public void cleanGitIgnore() throws IOException {
		ValidationUtils.fixGitEmptyFoldersProblem();
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);
	
	}

	
	
	
	@Test
	public void checkTesCaseStructureIsValid() throws IOException {
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s2 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s2, 20);
		Assert.assertTrue("size stays the same, messages  should be added while context exists" + s1 + "==" + s2, s1==s2);
		ValidationHolder.validationHolder().forEach( (k,v)-> log.info("Validation erros: " + k + " - " +  Joiner.on(";").join(v) )  );
	
	}


	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		ValidationUtils.validateConnectorRefExists();
		ValidationHolder.validationHolder().forEach( (k,v)-> log.info("Validation erros: " + k + " - " +  Joiner.on(";").join(v) )  );
		Assert.assertTrue(ValidationHolder.validationHolder().size()==0);
		
	}
	
	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesNotValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName()+"-missed-refs");
		ValidationUtils.validateConnectorRefExists();
		ValidationHolder.validationHolder().forEach( (k,v)-> log.info("Validation erros: " + k + " - " +  Joiner.on(";").join(v) )  );
		Assert.assertTrue(ValidationHolder.validationHolder().size()==3);
	}
	
	
}
