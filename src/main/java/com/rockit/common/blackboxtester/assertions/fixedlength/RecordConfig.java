/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import java.util.ArrayList;

/**
 *
 * @author muellerw
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
