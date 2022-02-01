/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.service;

import com.example.koerber.dao.IMachineDao;
import com.example.koerber.exception.DataBaseException;
import com.example.koerber.exception.MachineNotFoundException;
import com.example.koerber.model.Machine;
import com.example.koerber.model.MachineDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mario
 */
@Service
@Transactional
public class MachineService implements IService<Long, MachineDto> {

    @Autowired
    private IMachineDao machineDao;

    @Override
    public MachineDto insert(MachineDto dto) {

        validate(dto);
        dto.setLastModified(LocalDateTime.now());
        if (dto.getEnabled() == null) {
            dto.setEnabled(Boolean.TRUE);
        }

        Machine machine = dto.build();
        if (dto.getParent() == null) {

        } else {
            Optional<Machine> parent = machineDao.findById(dto.getParent().getId());
            if (parent.isPresent()) {
                machine.setParent(parent.get());
            }
        }
        machineDao.save(machine);
        return machine.buildDto();
    }

    @Override
    public MachineDto update(MachineDto dto) {
        validate(dto);
        Optional<Machine> existingMachine = machineDao.findById(dto.getId());

        if (existingMachine.isPresent()) {
            Machine machineUpdate = existingMachine.get();

            machineUpdate.setName(dto.getName());
            machineUpdate.setDescription(dto.getDescription());
            machineUpdate.setLastModified(LocalDateTime.now());
            machineUpdate.setType(dto.getType() == null ? null : dto.getType().getCode());
            machineUpdate.setEnabled(dto.getEnabled());
            if (dto.getParent() != null) {
                Optional<Machine> existingParent = machineDao.findById(dto.getParent().getId());
                if (existingParent.isPresent()) {
                    machineUpdate.setParent(existingParent.get());
                }
            }

            machineDao.save(machineUpdate);
            return machineUpdate.buildDto();
        } else {
            throw new MachineNotFoundException("Machine not found with id : " + dto.getId());
        }

    }

    @Override
    public List<MachineDto> list() {
        List<Machine> allMachines = machineDao.findAll();
        List<MachineDto> to_return = new ArrayList<>();
        allMachines.stream().forEach(f -> {
            to_return.add(f.buildDto());
        });
        return to_return;
    }

    @Override
    public MachineDto getById(String id
    ) {
        Optional<Machine> existingMachine = machineDao.findById(id);

        if (existingMachine.isPresent()) {
            return existingMachine.get().buildDto();
        } else {
            throw new MachineNotFoundException("Machine not found with id : " + id);
        }

    }

    @Override
    public void deleteById(String id
    ) {
        Optional<Machine> existingMachine = machineDao.findById(id);

        if (existingMachine.isPresent()) {
            machineDao.delete(existingMachine.get());
        } else {
            throw new MachineNotFoundException("Machine not found with id : " + id);
        }

    }

    @Override
    public void deleteAll() {
        machineDao.deleteAll();
    }

    public void validate(MachineDto machine) {
        validateName(machine);
        validateDescription(machine);
        validateType(machine);
        validateParent(machine);
    }

    public void validateName(MachineDto machine) {
        if (StringUtils.isBlank(machine.getName())) {
            throw new DataBaseException("The name of the machine is mandatory!");
        }
        if (machine.getName().length() > 50) {
            throw new DataBaseException("The name of the machine max size is 50!");
        }
    }

    public void validateDescription(MachineDto machine) {
        if (StringUtils.isNotBlank(machine.getDescription())) {
            if (machine.getDescription().length() > 150) {
                throw new DataBaseException("The description of the machine max size is 150!");
            }
        }
    }

    public void validateParent(MachineDto machine) {
        if (machine.getParent() != null) {
            Optional<Machine> parent = machineDao.findById(machine.getParent().getId());
            if (!parent.isPresent()) {
                throw new DataBaseException("The parent doesnt exist on database!");
            }
            List<String> ids = new ArrayList<>();
            validateCircularDependency(machine, ids);
        }
    }

    public void validateType(MachineDto machine) {
        if (machine.getType() == null) {
            throw new DataBaseException("The type of the machine is mandatory!");
        }

    }

    public void validateCircularDependency(MachineDto dto, List<String> ids) {
        if (dto.getId() != null) {
            ids.add(dto.getId());

        }
        if (dto.getParent() != null) {
            if (ids.contains(dto.getParent().getId())) {
                throw new DataBaseException("Circular Dependency");
            }
            validateCircularDependency(dto.getParent(), ids);
        }
    }

}
//i. Name – String with max size of 50 chars, mandatory
//ii. Description – String with max size of 150 chars, optional
//iii. Parentid – ID of another object, optional. Ensure that the parent object must be
//filled in the system.
//iv. Type – Enum with options DUMMY, REAL. Mandatory, ensure that the value is
//always valid.
//v. lastModified – Can only be changed by the system and reflects the date of the
//last modification
//vi. Enabled – Boolean, defaults to true. Optional.
