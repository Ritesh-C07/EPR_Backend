package com.cdac.erp.feature.module.controller;

import com.cdac.erp.core.model.Module;
import com.cdac.erp.feature.module.dto.ModuleCreateRequest;
import com.cdac.erp.feature.module.dto.ModuleResponse;
import com.cdac.erp.feature.module.service.IModuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/modules")
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody ModuleCreateRequest createRequest) {
        Module newModule = moduleService.createModule(createRequest);
        return new ResponseEntity<>(newModule, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ModuleResponse> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{moduleId}")
    public ModuleResponse getModuleById(@PathVariable String moduleId) {
        return moduleService.getModuleById(moduleId);
    }
}