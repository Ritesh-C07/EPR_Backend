package com.cdac.erp.feature.module.service;

import com.cdac.erp.core.model.Module;
import com.cdac.erp.feature.module.dto.ModuleCreateRequest;
import com.cdac.erp.feature.module.dto.ModuleResponse;
import com.cdac.erp.feature.module.dto.ModuleUpdateRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModuleService {
	Module createModule(ModuleCreateRequest createRequest);

//	List<ModuleResponse> getAllModules();
	
	Page<ModuleResponse> getAllModules(Pageable pageable);
	
	ModuleResponse getModuleById(String moduleId);

	ModuleResponse updateModule(String moduleId, ModuleUpdateRequest request);

	void deleteModule(String moduleId);
}