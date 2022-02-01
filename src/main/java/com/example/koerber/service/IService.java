/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mario
 */
public interface IService<PK extends Serializable, T> {

    T insert(T t);

    T update(T t);

    List<T> list();

    T getById(String id);

    void deleteById(String id);

    void deleteAll();
}
