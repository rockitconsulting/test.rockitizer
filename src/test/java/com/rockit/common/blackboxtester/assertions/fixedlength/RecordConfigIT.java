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
