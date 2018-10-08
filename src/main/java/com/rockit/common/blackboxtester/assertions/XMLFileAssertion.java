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

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

public class XMLFileAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(XMLFileAssertion.class.getName());


	private List<String> tokens = new ArrayList<>();
	private List<String>  attrs = new ArrayList<>();

	private DiffBuilder diffBuilder;
	
	BuilderContext context = new XMLFileAssertion.BuilderContext();


	private String step;

	
	public XMLFileAssertion(String step) {
		this.step = step;
	}
	
	/** 
	 * @Deprecated use the XMLFileAssertion("step/connector")
	 */
	public XMLFileAssertion(String recordPath, String replayPath, String step) {
		setRecordPath(replayPath);
		setReplayPath(replayPath);
		this.step = step;
	}
	
	
	@Override
	public void proceed() {
		File recordFolder = new File(recordPath+ File.separator + step);
		File replayFolder = new File(replayPath+ File.separator + step);

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + File.separator + relativePath);

			if (recordFile.isFile() && replayFile.isFile()) {
				LOGGER.debug("xmlasserting " + recordFile.getPath()  + " with "+ replayFile.getPath() );

				
				try { 
				    compare(Input.fromFile(recordFile), Input.fromFile(replayFile)).build();
				} catch (AssertionError e) {
					throw new AssertionException(XMLFileAssertion.class.getSimpleName() + ":" +  relativePath , e);
				}
				
				
			}
		}
	}

	public XMLFileAssertion ignoreAttrs(List<String> attrs) {
		this.attrs = attrs;
		return this;
	}

	public XMLFileAssertion ignore(List<String> tokens) {
		this.tokens = tokens;
		return this;
	}

	/**
	 * Important!!! only test visibility from the same package
	 * called automatically by proceed()
	 * @return
	 */
	protected XMLFileAssertion build() {
		Diff diff= diffBuilder.build();
		assertFalse(diff.getDifferences().toString() , diff.hasDifferences());
		return this;
	}	
	
	
	
	public XMLFileAssertion ignoreWhitespaces() {
		context.ignoreWhitespaces = true;		
		return this;
	}

	
	
	public XMLFileAssertion withNodeMatcher(ElementSelector selector) {
		context.selector = selector;
		return this;
	}
	
	public XMLFileAssertion checkForSimilar() {
		context.checkForSimilar = true;
		return this;
	}
	
	public XMLFileAssertion checkForIdentical() {
		context.checkForIdentical = true;
		return this;
	}
	
	
	
	public XMLFileAssertion compare(Builder in1, Builder in2) {

		diffBuilder = DiffBuilder.compare(in1).withTest(in2).withNodeFilter(new Predicate<Node>() {
			public boolean test(Node node) {
				LOGGER.trace("current node " + node.getNodeName() + " result "
						+ String.valueOf(!tokens.contains(node.getNodeName())));
				
				return !tokens.contains(node.getNodeName());
			}
		}).withAttributeFilter(new Predicate<Attr>() {
			public boolean test(Attr a) {
				LOGGER.trace("current attr " + a.getName() + " result "
						+ String.valueOf(!attrs.contains(a.getName())));
				
				return !attrs.contains(a.getName());
			}
		
		});

		if(context.ignoreWhitespaces){
			diffBuilder.ignoreWhitespace();
		}if(context.checkForIdentical){
			diffBuilder.checkForIdentical();
		}if(context.checkForSimilar){
			diffBuilder.checkForSimilar();
		}if(context.selector!=null){
			diffBuilder.withNodeMatcher(new DefaultNodeMatcher(context.selector));
		}
		
		return this;
	}
	
	private class BuilderContext {
		
		private boolean ignoreWhitespaces, checkForIdentical, checkForSimilar;
		private ElementSelector selector;
		
	}

}
