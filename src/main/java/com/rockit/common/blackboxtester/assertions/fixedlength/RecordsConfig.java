/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockit.common.blackboxtester.assertions.fixedlength;

import java.util.HashMap;

/**
 *
 * @author muellerw
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
