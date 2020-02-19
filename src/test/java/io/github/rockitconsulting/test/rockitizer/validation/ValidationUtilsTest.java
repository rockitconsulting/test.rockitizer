package io.github.rockitconsulting.test.rockitizer.validation;

import io.github.rockitconsulting.test.rockitizer.cli.ValidationCLITest;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.ValidationHolder;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class ValidationUtilsTest {
	public static Logger log = Logger.getLogger(ValidationCLITest.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";

	String rootPathTestSrc = rootPath + relPath;

	@Test
	public void cleanGitIgnore() throws IOException {
		ValidationUtils.fixGitEmptyFoldersProblem(new File(rootPathTestSrc));
		ValidationUtils.cleanGitIgnore(new File(rootPathTestSrc));
		ValidationUtils.fixGitEmptyFoldersProblem(new File(rootPathTestSrc));
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);
	
	}

	
	
	
	@Test
	public void checkTesCaseStructureIsValid() throws IOException {
		ValidationUtils.cleanGitIgnore(new File(rootPathTestSrc));
		
		ValidationUtils.fixGitEmptyFoldersProblem(new File(rootPathTestSrc));
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);
		ValidationUtils.fixGitEmptyFoldersProblem(new File(rootPathTestSrc));
		int s2 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s2, 20);
		Assert.assertTrue("size stays the same, messages  should be added while context exists" + s1 + "==" + s2, s1==s2);
		ValidationHolder.validationHolder().forEach( (k,v)-> log.info("Validation erros: " + k + " - " +  Joiner.on(";").join(v) )  );
	
	}

}
