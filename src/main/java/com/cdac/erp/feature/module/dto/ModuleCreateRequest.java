package com.cdac.erp.feature.module.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModuleCreateRequest {
    private String moduleId; // e.g., "CS101"
    private String moduleName;
    private Integer departmentId;
}