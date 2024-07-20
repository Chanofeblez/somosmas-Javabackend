package com.somosmas.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.somosmas.miembros.Miembro;

@JsonPropertyOrder({"username","message","jwt","status"})
public record AuthResponseDTO(String username,
                              String message,
                              String jwt,
                              boolean status) {
}
