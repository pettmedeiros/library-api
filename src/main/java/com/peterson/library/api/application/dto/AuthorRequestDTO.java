package com.peterson.library.api.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthorRequestDTO(

    @NotBlank(message = "Nome é obrigatório!")
    @JsonProperty("name")
    String name,

    @NotBlank(message = "E-mail é obrigatório!")
    @Email(message = "E-mail está inválido")
    @JsonProperty("email")
    String email,

    @JsonProperty("biography")
    String biography

) {
    @JsonCreator
    public AuthorRequestDTO {
        // construtor compacto do record
    }
}