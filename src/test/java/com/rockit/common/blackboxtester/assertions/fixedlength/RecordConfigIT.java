/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author muellerw
 */
public class RecordConfigIT {
    
    public RecordConfigIT() {
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
     * Test of getOffsetAt method, of class RecordConfig.
     */
    @Test
    public void testGetOffsetAt() {
        System.out.println("getOffsetAt");
        String config = "2;5;8;-7;6;2;-4;1";
//        String stringForSplit = "22555558888888877777776666662244441";
        int pos = 0;
        RecordConfig instance = new RecordConfig(config);
        
        System.out.println("Record: " + instance.getStart(2) + " length: " + instance.getEnd(2));
        int expResult = 0;
        int result = instance.getStart(pos);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLengthAt method, of class RecordConfig.
     */
    @Test
    public void testGetLengthAt() {
        System.out.println("getLengthAt");
        int pos = 0;
        int expResult = 0;
        String config = "2;5;8;-7;6;2;-4;1";
        RecordConfig instance = new RecordConfig(config);
        int result = instance.getEnd(pos);
        assertEquals(expResult, result);
    }
    
}
