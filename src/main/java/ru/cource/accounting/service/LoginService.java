package ru.cource.accounting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.cource.accounting.dto.security.JwtRequestDto;
import ru.cource.accounting.security.JwtUserDetails;

public interface LoginService {

    JwtUserDetails login(JwtRequestDto user) throws JsonProcessingException;

    JwtUserDetails getCurrentUser();
}
