/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

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

public class DifferenceBuilder {

    private final List<String> actualDifferences = new ArrayList<String>();
    private final FixedLengthFileAssertion fa;
    private String fileName;

    public DifferenceBuilder(FixedLengthFileAssertion fa) {
        this.fa = fa;
    }

    private void doLineDiff(int linePosition, String recorded, String replayed) {
        if (recorded.length() == 0 || replayed.length() == 0) {
            add(recorded, replayed, linePosition, 99999);
        } else {
            List<String> recordedTokenized = parse(recorded, fa.getRecordConfig(), true);
            List<String> replayedTokenized = parse(replayed, fa.getRecordConfig(), false);
            for (int tokenInLine = 0; tokenInLine < recordedTokenized.size(); tokenInLine++) {
                if (fa.getRecordConfig().doDiff(tokenInLine) && !recordedTokenized.get(tokenInLine).equalsIgnoreCase(replayedTokenized.get(tokenInLine))) {
                    add(recordedTokenized.get(tokenInLine), replayedTokenized.get(tokenInLine), linePosition, tokenInLine);
                }
            }
        }

    }

    /**
     * Reads record and replay and writes them into strings.
     * @param fileName
     */
    public void build(String fileName) {
        //    Assert.assertTrue("In File: " + fileName + " count of replayed messages " + fa.getReplayed().size() + " is not the expected count of " + fa.getRecorded().size(), fa.getReplayed().size() == fa.getRecorded().size());
        this.fileName = fileName;
        Iterator<String> replayed = fa.getReplayed().iterator();
        Iterator<String> recorded = fa.getRecorded().iterator();
        int linePosition = 1;
        while ((replayed.hasNext()) || (recorded.hasNext())) {
            String recordedLine = recorded.hasNext() ? recorded.next() : "";
            String replayedLine = replayed.hasNext() ? replayed.next() : "";
            doLineDiff(linePosition, recordedLine, replayedLine);
            linePosition += 1;
        }
    }

    /**
     * Parses test record items
     * @param lineToParse
     * @param rc
     * @param isRecorded
     * @return
     */
    private List<String> parse(String lineToParse, RecordConfig rc, boolean isRecorded) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < rc.getTokenCount(); i++) {
            try {
                result.add(lineToParse.substring(rc.getStart(i), rc.getEnd(i)));
            } catch (StringIndexOutOfBoundsException stob) {
                StringBuilder sb = new StringBuilder("");
                if (isRecorded) {
                    sb.append("Expected");
                } else {
                    sb.append("Received");
                }
                sb.append(" content of length: ");
                sb.append((rc.getEnd(i) - rc.getStart(i))).append(" but was: ");
                String content = lineToParse.length() >= rc.getStart(i) ? lineToParse.substring(rc.getStart(i), lineToParse.length()) : "\"\"";
                sb.append(content);
                result.add(sb.toString());
            }
        }
        return result;
    }

    /**
     * Builds error reports
     * @param expected
     * @param actual
     * @param linePos
     * @param tokenPosition
     */
    private void add(String expected, String actual, int linePos, int tokenPosition) {
        StringBuilder sb = new StringBuilder("[Error in file: ").append(this.fileName).append(" at line: ");
        sb.append(linePos)
                .append(" at pos ")
                .append((tokenPosition + 1))
                .append(" expected: ")
                .append(expected)
                .append(" vs. actual: ")
                .append(actual)
                .append("]\n");
        actualDifferences.add(sb.toString());
    }

    
    
    /**
     * Gives proper formatting to the presentation of differences.
     * @return
     */
    private String formatDifferences() {
        StringBuilder sb = new StringBuilder("");
        if (actualDifferences.size() > 0) {
            sb.append("During compare ").append(actualDifferences.size()).append(" errors detected!");
            sb.append(System.lineSeparator());
            Iterator<String> ad = actualDifferences.iterator();
            while (ad.hasNext()) {
                sb.append(ad.next());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public void doAssert() {
        Assert.assertEquals("", formatDifferences());
    }
}
