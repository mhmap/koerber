/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.controller;

import com.example.koerber.model.Machine;
import com.example.koerber.model.MachineDto;
import com.example.koerber.service.MachineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mario
 */
@RestController
public class MachineController {

    @Autowired
    private MachineService machineService;

    @GetMapping(value = "/machines", produces = { "application/json", "application/xml" })
    public ResponseEntity getAllMachine() {
        try {
            return ResponseEntity.ok().body(machineService.list());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/machines/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity getMachineById(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(machineService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/machines", produces = { "application/json", "application/xml" })
    public ResponseEntity createMachine(@RequestBody MachineDto dto) {
        try {
            return ResponseEntity.ok().body(this.machineService.insert(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/machines/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity updateMachine(@PathVariable String id, @RequestBody MachineDto dto) {
        try {
            dto.setId(id);
            return ResponseEntity.ok().body(this.machineService.update(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/machines/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity deleteMachine(@PathVariable String id) {
        try {
            this.machineService.deleteById(id);
            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
