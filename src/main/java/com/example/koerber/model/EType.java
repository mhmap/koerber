/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.model;

import lombok.Getter;

/**
 *
 * @author mario
 */
@Getter
public enum EType {
    DUMMY("DUMMY", "Dummy"),
    REAL("REAL", "Real");

    private String code;
    private String value;

    EType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static EType byCode(String code) {
        for (EType type : EType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalStateException("Can't find EType with code :" + code);
    }

    public static EType byValue(String val) {
        for (EType type : EType.values()) {
            if (type.getValue().equals(val)) {
                return type;
            }
        }
        throw new IllegalStateException("Can't find EType with value :" + val);
    }

}
