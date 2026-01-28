package com.maximarcos.miplan.dto.action;

public record ActionRequestDto(String title, String description, Long planId, Long userId) {}

// luego modificar a que userId no venga por parametro, sino por la autenticación del usuario