package com.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
} 