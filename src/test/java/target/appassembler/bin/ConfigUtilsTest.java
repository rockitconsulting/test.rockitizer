package target.appassembler.bin;

import org.junit.Assert;
import org.junit.Test;

public class ConfigUtilsTest {

	@Test
	public void testInternal() {

		String path = "C:\\rockit\\projects\\github\\test.rockitizer";

		path = path.replace("\\", "/") + "/src/test/resources/";
		System.out.println(path);

		Assert.assertEquals("C:/rockit/projects/github/test.rockitizer/src/test/resources/", path);

	}

	@Test
	public void testExternal() {

		String path = "C:\\rockit\\projects\\github\\test.rockitizer\\target\\appassembler\\bin";

		System.setProperty("basedir", "something");
		System.setProperty("app.home", "something");

		if (System.getProperty("basedir") != null && System.getProperty("app.home") != null) {
			path = path.replace("\\bin", "").replace("\\target", "").replace("\\appassembler", "");
		}

		path = path.replace("\\", "/") + "/src/test/resources/";
		System.out.println(path);

		Assert.assertEquals("C:/rockit/projects/github/test.rockitizer/src/test/resources/", path);

		System.clearProperty("basedir");
		System.clearProperty("app.home");

	}
}
