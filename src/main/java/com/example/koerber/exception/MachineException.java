/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author mario
 */
@ResponseStatus
public class MachineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MachineException(String message) {
        super(message);
    }

    public MachineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
