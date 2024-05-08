/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import java.util.ArrayList;

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

public class RecordConfig {

    private final ArrayList<Integer> start = new ArrayList<>();
    private final ArrayList<Integer> end = new ArrayList<>();
    private final ArrayList<Boolean> doDiff = new ArrayList<>();
    private int tokencount = 0;
    private int offset = 0;

    public RecordConfig(String recordConfig) {
        String[] splittedRecord = recordConfig.split(";");
        offset = 0;
        for (String part : splittedRecord) {
            int length = Integer.valueOf(part);
            tokencount += 1;
            start.add(offset);
            offset += Math.abs(length);
            end.add(offset);
            if (length > 0) {
                doDiff.add(Boolean.TRUE);
            }else{
                doDiff.add(Boolean.FALSE);
            }
        }
    }

    public int getStart(int pos) {
        return start.get(pos);
    }

    public int getEnd(int pos) {
        return end.get(pos);
    }

    public int getTokenCount() {
        return tokencount;
    }

    public boolean doDiff(int pos) {
        return doDiff.get(pos);
    }
}
