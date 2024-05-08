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

import com.rockit.common.blackboxtester.assertions.FixedLengthFileAssertion;

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
        FixedLengthFileAssertion fixedLengthFileAssertion = new FixedLengthFileAssertion("test1", config);
        fixedLengthFileAssertion.setRecordPath("c://temp//a//record");
        fixedLengthFileAssertion.setRecordPath("c://temp//a//replay");
        fixedLengthFileAssertion.proceed();
    }
    
}
