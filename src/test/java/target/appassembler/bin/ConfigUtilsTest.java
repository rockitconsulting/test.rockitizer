package target.appassembler.bin;

import org.junit.Assert;
import org.junit.Test;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

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
