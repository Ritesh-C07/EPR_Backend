package com.cdac.erp.feature.module.service;

import com.cdac.erp.common.exception.ResourceNotFoundException;
import com.cdac.erp.core.model.Department;
import com.cdac.erp.core.model.Module;
import com.cdac.erp.core.repository.DepartmentRepository;
import com.cdac.erp.core.repository.ModuleRepository;
import com.cdac.erp.feature.module.dto.ModuleCreateRequest;
import com.cdac.erp.feature.module.dto.ModuleResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements IModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public Module createModule(ModuleCreateRequest createRequest) {
		// Find the department to associate with the module
		Department department = departmentRepository.findById(createRequest.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not found with id: " + createRequest.getDepartmentId()));

		Module module = new Module();
		module.setModuleId(createRequest.getModuleId());
		module.setModuleName(createRequest.getModuleName());
		module.setDepartment(department);

		return moduleRepository.save(module);
	}

	@Override
	public List<ModuleResponse> getAllModules() {
		return moduleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public ModuleResponse getModuleById(String moduleId) {
		Module module = moduleRepository.findById(moduleId)
				.orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + moduleId));

		return convertToDto(module);
	}

	private ModuleResponse convertToDto(Module module) {
		ModuleResponse dto = new ModuleResponse();
		dto.setModuleId(module.getModuleId());
		dto.setModuleName(module.getModuleName());
		// This safely triggers the lazy loading for the department
		if (module.getDepartment() != null) {
			dto.setDepartmentName(module.getDepartment().getDepartmentName());
		}
		return dto;
	}
}