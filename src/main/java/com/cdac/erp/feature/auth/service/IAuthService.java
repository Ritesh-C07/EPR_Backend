package com.cdac.erp.feature.auth.service;

import java.util.List;

import com.cdac.erp.feature.auth.dto.AdminRegisterRequest;
import com.cdac.erp.feature.auth.dto.AdminResponse;
import com.cdac.erp.feature.auth.dto.AdminUpdateRequest;
import com.cdac.erp.feature.auth.dto.AuthResponse;
import com.cdac.erp.feature.auth.dto.LoginRequest;

public interface IAuthService {

	AdminResponse registerAdmin(AdminRegisterRequest registerRequest);

	AuthResponse login(LoginRequest loginRequest);

	List<AdminResponse> getAllAdmins();

	AdminResponse getAdminById(Integer adminId);

	AdminResponse updateAdmin(Integer adminId, AdminUpdateRequest request);

	void deleteAdmin(Integer adminId);
}