/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author muellerw
 */
public class DifferenceBuilderIT {
    
    public DifferenceBuilderIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of build method, of class DifferenceBuilder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        System.out.println("getOffsetAt");
        String config = "test1#2;5;-2;6;2;-4;1";
        new FixedLengthFileAssertion("c://temp//a//record","c://temp//a//replay","test1", config).proceed();
    }
    
}
