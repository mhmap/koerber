/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.koerber.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author mario
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Machine")
public class Machine {

    @Id
    private String id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    @DBRef
    private Machine parent;
    private String type;
    private LocalDateTime lastModified;
    private Boolean enabled;


    public MachineDto buildDto() {
        EType type = StringUtils.isBlank(getType()) ? null : EType.byCode(getType());
        return MachineDto.builder()
                .id(getId())
                .name(getName())
                .parent(getParent() == null ? null : getParent().buildDto())
                .type(type)
                .description(getDescription())
                .lastModified(getLastModified())
                .enabled(getEnabled())
                .build();
    }

}
