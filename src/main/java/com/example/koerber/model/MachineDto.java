/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mario
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {

    private String  id;
    private String name;
    private String description;
    private MachineDto parent;
    private EType type;
    private LocalDateTime lastModified;
    private Boolean enabled;

    public Machine build() {
        return Machine.builder()
                .id(getId())
                .name(getName())
                .parent(getParent() == null ? null : getParent().build())
                .type(getType() == null ? null : getType().getCode())
                .description(getDescription())
                .lastModified(getLastModified())
                .enabled(getEnabled())
                .build();
    }

}
