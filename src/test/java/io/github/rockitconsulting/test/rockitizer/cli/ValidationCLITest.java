package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.validation.model.ValidationHolder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidationCLITest {

	
	
	@Before
	public void before() {
		ValidationHolder.reset();
	}
	
	
	
	@Test
	public void validateConnectorConfiiguration() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName()+"-Valid");
		
		configuration().getResourcesHolder().getDbConnectors().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		
		configuration().getResourcesHolder().getDbDataSources().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		
		configuration().getResourcesHolder().getFileConnectors().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		configuration().getResourcesHolder().getHttpConnectors().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		configuration().getResourcesHolder().getKeyStores().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		configuration().getResourcesHolder().getMqConnectors().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		configuration().getResourcesHolder().getMqDataSources().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		configuration().getResourcesHolder().getScpConnectors().forEach( c -> ValidationHolder.validationHolder().addAll(c.validate() ) );
		
		
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size()==0);
		
	}

}
