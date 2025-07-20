package com.cdac.erp.feature.module.service;

import com.cdac.erp.core.model.Module;
import com.cdac.erp.feature.module.dto.ModuleCreateRequest;
import com.cdac.erp.feature.module.dto.ModuleResponse;

import java.util.List;

public interface IModuleService {
	Module createModule(ModuleCreateRequest createRequest);

	List<ModuleResponse> getAllModules();

	ModuleResponse getModuleById(String moduleId);
}