package com.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String email;
    private Map<String, Object> customFields;
    private boolean emailVerified;
    private boolean phoneVerified;
} 