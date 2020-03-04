/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import java.util.HashMap;

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

public class RecordsConfig {

    private static RecordsConfig instance;
    private final HashMap<String, RecordConfig> configHolder;

    public static RecordsConfig getInstance() {
        if (instance == null) {
            return new RecordsConfig();
        } else {
            return instance;
        }
    }

    public RecordConfig getRecordConfig(String recordConfigString) {
        String[] nameAndConfig = recordConfigString.split("#");

        if (!this.configHolder.containsKey(nameAndConfig[0])) {
            this.configHolder.put(nameAndConfig[0], new RecordConfig(nameAndConfig[1]));
        }
        return this.configHolder.get(nameAndConfig[0]);
    }

    private RecordsConfig() {
        configHolder = new HashMap<>();
    }
}
