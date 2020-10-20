package io.github.rockitconsulting.test.rockitizer.configuration.snakeyaml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

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

public class ConfigurationModelRepresenter extends Representer {

	
    public ConfigurationModelRepresenter() {
        super();
    }

    public ConfigurationModelRepresenter(DumperOptions options) {
        super(options);
        
    }

    
    /**
     * ignore NULL Objects and EMPTY collections
     *
     */
  
    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
            Object propertyValue, Tag customTag) {
        NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue,
                customTag);
        Node valueNode = tuple.getValueNode();
        if (Tag.NULL.equals(valueNode.getTag())) {
            return null;// skip 'null' values
        }
        if (valueNode instanceof CollectionNode) {
            if (Tag.SEQ.equals(valueNode.getTag())) {
                SequenceNode seq = (SequenceNode) valueNode;
                if (seq.getValue().isEmpty()) {
                    return null;// skip empty lists
                }
            }
            if (Tag.MAP.equals(valueNode.getTag())) {
                MappingNode seq = (MappingNode) valueNode;
                if (seq.getValue().isEmpty()) {
                    return null;// skip empty maps
                }
            }
        }
        return tuple;
    }


    
    /**
     * Custom ordered properties
     *
     */
    
    protected Set<Property> getProperties(Class<? extends Object> type) {
    	Set<Property> propertySet;
    	if (typeDefinitions.containsKey(type)) {
    		propertySet = typeDefinitions.get(type).getProperties();
        }
    	
    	propertySet =  getPropertyUtils().getProperties(type);
    	
    	List<Property> propsList = new ArrayList<>(propertySet);
    	Collections.sort(propsList, new BeanPropertyComparator());
    	
    	return new LinkedHashSet<>(propsList);
    }


    
    
    List<String> propertyOrder = Arrays.asList(
    		new String[]{
    				"mqConnectors","dbConnectors","httpConnectors","fileConnectors","scpConnectors","mqDataSources","dbDataSources","keyStores","payloadReplacer",  // Resources top-level
    				"testCases","testSteps","testCaseName","testCaseDescription","testStepName","testStepDescription","connectorRefs","conRefId","conRefDescription","payloads","fileName", // testcases top-level
    				"id","type","queue","query", "url","method","path","contentType", "userAgent","timeout", //  connectors
    				"qmgr","channel","host","port","user","password","dsRefId" //  ds
    		});
    
    
    
	class BeanPropertyComparator implements Comparator<Property> {
		public int compare(Property p1, Property p2) {
			if(!propertyOrder.contains(p1.getName())) {
				throw new RuntimeException("Property : " + p1.getName() + " is not known in "+this.getClass().getSimpleName()+"#propertyOrder: " + propertyOrder + ". Please add it " );
			}
			if(!propertyOrder.contains(p2.getName())) {
				throw new RuntimeException("Property : " + p2.getName() + " is not known in "+this.getClass().getSimpleName()+"#propertyOrder: " + propertyOrder + ". Please add it " );
			}
			
			
	        if (  
	        	p1.getType().getCanonicalName().contains("util") && !p2.getType().getCanonicalName().contains("util") ||
	        	 
//	        	 !(p1.getName().endsWith("Name")|| p1.getName().endsWith("name")  ) && (p2.getName().endsWith("Name")|| p2.getName().endsWith("name") ) ||
	        	 propertyOrder.indexOf(p1.getName()) > propertyOrder.indexOf(p2.getName()) 
//	             p1.getName().length() > p2.getName().length() 
	           ) {
	           return 1;
	        } else {
	            return -1;
	        } // returning 0 would merge keys
	    }
	}
}
