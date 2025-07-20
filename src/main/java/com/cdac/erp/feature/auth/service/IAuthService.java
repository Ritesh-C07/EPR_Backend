package com.cdac.erp.feature.auth.service;

import com.cdac.erp.core.model.Admin;
import com.cdac.erp.feature.auth.dto.AdminRegisterRequest;
import com.cdac.erp.feature.auth.dto.AuthResponse;
import com.cdac.erp.feature.auth.dto.LoginRequest;

public interface IAuthService {

    Admin registerAdmin(AdminRegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}