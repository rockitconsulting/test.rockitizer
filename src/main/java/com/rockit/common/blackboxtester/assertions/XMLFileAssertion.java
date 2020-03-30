package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.builder.Input.Builder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelector;
import org.xmlunit.util.Predicate;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class XMLFileAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(XMLFileAssertion.class.getName());

	private List<String> tokens = new ArrayList<>();
	private List<String> attrs = new ArrayList<>();

	private DiffBuilder diffBuilder;

	BuilderContext context = new XMLFileAssertion.BuilderContext();

	@SuppressWarnings("unused")
	private XMLFileAssertion() {
	}

	/**
	 * XMLUnit for all payloads on the step level, including all connector subfolders
	 * 
	 * @param step folder under testcases
	 */
	public XMLFileAssertion(String step) {
		this.relPath = File.separator + step;
	}

	/**
	 * XMLUnit for all payloads on the step/connector level
     *   
	 * @param step  -subfolder of testcase
	 * @param connector - subfolder of step
	 */
	public XMLFileAssertion(String step, String connector) {
		this.relPath = File.separator + step + File.separator + connector;
	}

	/** 
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#proceed()
	 */
	@Override
	public void proceed() {
		File recordFolder = new File(recordPath + relPath);
		File replayFolder = new File(replayPath + relPath);

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + File.separator + relativePath);

			if (recordFile.isFile() && replayFile.isFile()) {
				LOGGER.debug("xmlasserting " + recordFile.getPath() + " with " + replayFile.getPath());

				try {
					compare(Input.fromFile(recordFile), Input.fromFile(replayFile)).build();
				} catch (AssertionError e) {
					throw new AssertionException(XMLFileAssertion.class.getSimpleName() + ":" + relPath + "/" + relativePath, e);
				}

			}
		}
	}

	/**
	 * Ignoring the xml attributes
	 * 
	 * @param attrs  - list of strings with xml attributes to ignore
	 * @return
	 */
	public XMLFileAssertion ignoreAttrs(List<String> attrs) {
		this.attrs = attrs;
		return this;
	}

	/**
	 * Ignoring the xml nodes 
	 * @param tokens - list xml nodes to ignore 
	 * @return
	 */
	public XMLFileAssertion ignore(List<String> tokens) {
		this.tokens = tokens;
		return this;
	}

	/**
	 * Important!!! only test visibility from the same package called
	 * automatically by proceed()
	 * 
	 * @return
	 */
	protected XMLFileAssertion build() {
		Diff diff = diffBuilder.normalizeWhitespace().build();
		assertFalse(diff.getDifferences().toString(), diff.hasDifferences());
		return this;
	}

	/**
	 * Ignore whitespaces
	 * @return
	 */
	public XMLFileAssertion ignoreWhitespaces() {
		context.ignoreWhitespaces = true;
		return this;
	}

	/**
	 * @param selector - @see ElementSelector
	 * @return
	 */
	public XMLFileAssertion withNodeMatcher(ElementSelector selector) {
		context.selector = selector;
		return this;
	}

	/**
	 * @see DiffBuilder#checkForSimilar()
	 * @return
	 */
	public XMLFileAssertion checkForSimilar() {
		context.checkForSimilar = true;
		return this;
	}

	/**
	 * @see DiffBuilder#checkForIdentical()
	 * @return
	 */
	public XMLFileAssertion checkForIdentical() {
		context.checkForIdentical = true;
		return this;
	}

	protected XMLFileAssertion compare(Builder in1, Builder in2) {

		diffBuilder = DiffBuilder.compare(in1).withTest(in2).withNodeFilter(new Predicate<Node>() {
			public boolean test(Node node) {
				LOGGER.trace("current node " + node.getNodeName() + " result " + String.valueOf(!tokens.contains(node.getNodeName())));

				return !tokens.contains(node.getNodeName());
			}
		}).withAttributeFilter(new Predicate<Attr>() {
			public boolean test(Attr a) {
				LOGGER.trace("current attr " + a.getName() + " result " + String.valueOf(!attrs.contains(a.getName())));

				return !attrs.contains(a.getName());
			}

		});

		if (context.ignoreWhitespaces) {
			diffBuilder.ignoreWhitespace();
		}
		if (context.checkForIdentical) {
			diffBuilder.checkForIdentical();
		}
		if (context.checkForSimilar) {
			diffBuilder.checkForSimilar();
		}
		if (context.selector != null) {
			diffBuilder.withNodeMatcher(new DefaultNodeMatcher(context.selector));
		}

		return this;
	}

	private class BuilderContext {

		private boolean ignoreWhitespaces, checkForIdentical, checkForSimilar;
		private ElementSelector selector;

	}

	public String toString() {
		return this.getClass().getSimpleName() + "( path:\"" + (relPath.equalsIgnoreCase("") ? "\\" : relPath) + "\"" +
				
				(null!=tokens && !tokens.isEmpty()?", ignoreFields:\""+Joiner.on(",").join(tokens)+"\"":"" ) +
				(null!=attrs && attrs.isEmpty()?", ignoreAttrs:\""+Joiner.on(",").join(attrs)+"\"":"" ) +
				" )";
	}

}
