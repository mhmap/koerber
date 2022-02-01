/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.koerber.service;

import com.example.koerber.exception.DataBaseException;
import com.example.koerber.exception.MachineException;
import com.example.koerber.model.EType;
import com.example.koerber.model.MachineDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author mario
 */
@SpringBootTest
public class MachineServiceTest {
    
    @Autowired
    private MachineService machineService;
    
    public MachineServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void insert() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        
        MachineDto fromDB_m1 = machineService.getById(expected_m1.getId());
        
        assertEquals(m1.getName(), fromDB_m1.getName());
        assertEquals(m1.getDescription(), fromDB_m1.getDescription());
        assertEquals(m1.getParent(), fromDB_m1.getParent());
        assertEquals(m1.getType(), fromDB_m1.getType());
        assertEquals(m1.getLastModified(), fromDB_m1.getLastModified());
        assertEquals(m1.getEnabled(), fromDB_m1.getEnabled());
    }
    
    @Test
    public void update() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        expected_m1.setDescription("M1 Description Changed");
        
        machineService.update(expected_m1);
        MachineDto fromDB_m1 = machineService.getById(expected_m1.getId());
        assertEquals(expected_m1.getName(), fromDB_m1.getName());
        assertEquals(expected_m1.getDescription(), fromDB_m1.getDescription());
        Assertions.assertNotEquals(m1.getDescription(), fromDB_m1.getDescription());
        assertEquals(expected_m1.getParent(), fromDB_m1.getParent());
        assertEquals(expected_m1.getType(), fromDB_m1.getType());
        Assertions.assertNotEquals(expected_m1.getLastModified(), fromDB_m1.getLastModified());
        assertEquals(expected_m1.getEnabled(), fromDB_m1.getEnabled());
    }
    
    @Test
    public void updateCircularDependency() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        MachineDto m2 = buildMachineDto("M2", "M2 Description", EType.REAL, expected_m1, Boolean.FALSE);
        MachineDto expected_m2 = machineService.insert(m2);
        MachineDto m3 = buildMachineDto("M3", "M3 Description", EType.REAL, expected_m2, Boolean.FALSE);
        MachineDto expected_m3 = machineService.insert(m3);
        expected_m1.setParent(expected_m3);
        Exception exception = assertThrows(DataBaseException.class, () -> {
            machineService.update(expected_m1);
        });
        String expectedMessage = "Circular Dependency";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
        
        assertEquals(actualMessage, expectedMessage);
        
    }
    
    @Test
    public void list() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        MachineDto m2 = buildMachineDto("M2", "M2 Description", EType.REAL, expected_m1, Boolean.FALSE);
        MachineDto expected_m2 = machineService.insert(m2);
        MachineDto m3 = buildMachineDto("M3", "M3 Description", EType.REAL, expected_m2, Boolean.FALSE);
        MachineDto expected_m3 = machineService.insert(m3);
        MachineDto m4 = buildMachineDto("M4", "M4 Description", EType.REAL, expected_m3, Boolean.FALSE);
        MachineDto expected_m4 = machineService.insert(m4);
        
        List<MachineDto> list = machineService.list();
        assertEquals(list.size(), 4);
        
    }
    
    @Test
    public void getById() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        
        MachineDto fromDB_m1 = machineService.getById(expected_m1.getId());
        assertEquals(expected_m1.getId(), fromDB_m1.getId());
        assertEquals(expected_m1.getName(), fromDB_m1.getName());
        assertEquals(expected_m1.getDescription(), fromDB_m1.getDescription());
        assertEquals(expected_m1.getParent(), fromDB_m1.getParent());
        assertEquals(expected_m1.getType(), fromDB_m1.getType());
        assertEquals(expected_m1.getLastModified(), fromDB_m1.getLastModified());
        assertEquals(expected_m1.getEnabled(), fromDB_m1.getEnabled());
    }
    
    @Test
    public void deleteById() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        
        MachineDto fromDB_m1 = machineService.getById(expected_m1.getId());
        
        machineService.deleteById(fromDB_m1.getId());
        List<MachineDto> list = machineService.list();
        assertEquals(list.size(), 0);
    }
    
    @Test
    public void deleteAll() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        MachineDto m2 = buildMachineDto("M2", "M2 Description", EType.REAL, expected_m1, Boolean.FALSE);
        MachineDto expected_m2 = machineService.insert(m2);
        MachineDto m3 = buildMachineDto("M3", "M3 Description", EType.REAL, expected_m2, Boolean.FALSE);
        MachineDto expected_m3 = machineService.insert(m3);
        MachineDto m4 = buildMachineDto("M4", "M4 Description", EType.REAL, expected_m3, Boolean.FALSE);
        MachineDto expected_m4 = machineService.insert(m4);
        
        List<MachineDto> list = machineService.list();
        assertEquals(list.size(), 4);
        machineService.deleteAll();
        List<MachineDto> afterDeleteList = machineService.list();
        assertEquals(afterDeleteList.size(), 0);
    }
    
    @Test
    public void validate() {
        machineService.deleteAll();
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        MachineDto expected_m1 = machineService.insert(m1);
        MachineDto m2 = buildMachineDto("M2", "M2 Description", EType.REAL, expected_m1, Boolean.FALSE);
        machineService.validate(m2);
    }
    
    @Test
    public void validateName() {
        MachineDto m1 = buildMachineDto(null, "M1 Description", EType.REAL, null, Boolean.FALSE);
        Exception exception = assertThrows(DataBaseException.class, () -> {
            machineService.validateName(m1);
        });
        String expectedMessage = "The name of the machine is mandatory!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(actualMessage, expectedMessage);
        
        MachineDto m2 = buildMachineDto("MDASASJODOASDOASODJASOJJDOASDOJASOJDOASDJASOJDAOSDOASJDOOASJDOOASODASODAJSDASDASJDASODJAS", "M1 Description", EType.REAL, null, Boolean.FALSE);
        Exception exception2 = assertThrows(DataBaseException.class, () -> {
            machineService.validateName(m2);
        });
        String expectedMessage2 = "The name of the machine max size is 50!";
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.contains(expectedMessage2));
        assertEquals(actualMessage2, expectedMessage2);
        
    }
    
    @Test
    public void validateDescription() {
        MachineDto m2 = buildMachineDto("M1", "M1 Description DASASJODOASDOASODJASOJJDOASDOJASOJDOASDJASOJDAOSDOASJDOOASJDOOASODASODAJSDASDASJDASODJASDASASJODOASDOASODJASOJJDOASDOJASOJDOASDJASOJDAOSDOASJDOOASJDOOASODASODAJSDASDASJDASODJAS", EType.REAL, null, Boolean.FALSE);
        Exception exception2 = assertThrows(DataBaseException.class, () -> {
            machineService.validateDescription(m2);
        });
        String expectedMessage2 = "The description of the machine max size is 150!";
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.contains(expectedMessage2));
        assertEquals(actualMessage2, expectedMessage2);
    }
    
    @Test
    public void validateParent() {
        MachineDto m1 = buildMachineDto("M1", "M1 Description", EType.REAL, null, Boolean.FALSE);
        m1.setId("adsdasdas");
        MachineDto m2 = buildMachineDto("M2", "M2 Description", EType.REAL, m1, Boolean.FALSE);
        Exception exception = assertThrows(DataBaseException.class, () -> {
            machineService.validateParent(m2);
        });
        String expectedMessage = "The parent doesnt exist on database!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(actualMessage, expectedMessage);
    }
    
    @Test
    public void validateType() {
        MachineDto m1 = buildMachineDto("M1", "M1 Description", null, null, Boolean.FALSE);
        Exception exception = assertThrows(DataBaseException.class, () -> {
            machineService.validateType(m1);
        });
        String expectedMessage = "The type of the machine is mandatory!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(actualMessage, expectedMessage);
    }
    
    private MachineDto buildMachineDto(String name, String description, EType type, MachineDto parent, Boolean enabled) {
        
        return MachineDto.builder()
                .name(name)
                .description(description)
                .parent(parent)
                .type(type)
                .enabled(enabled)
                .build();
    }
}
