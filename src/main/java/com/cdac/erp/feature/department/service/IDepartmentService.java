package com.cdac.erp.feature.department.service;

import com.cdac.erp.core.model.Department;
import com.cdac.erp.feature.department.dto.DepartmentCreateRequest;
import com.cdac.erp.feature.department.dto.DepartmentUpdateRequest;

import java.util.List;

public interface IDepartmentService {

	List<Department> getAllDepartments();

	Department getDepartmentById(Integer departmentId);

	Department createDepartment(DepartmentCreateRequest createRequest);

	Department updateDepartment(Integer departmentId, DepartmentUpdateRequest updateRequest);

	void deleteDepartment(Integer departmentId);
}