package com.somosmas.dto;
import jakarta.validation.constraints.NotBlank;

public record StripeCheckDTO (@NotBlank String email){
}