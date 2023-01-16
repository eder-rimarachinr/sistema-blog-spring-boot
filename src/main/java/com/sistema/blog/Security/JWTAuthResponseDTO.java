package com.sistema.blog.Security;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {
    private String tokenAcceso;
    private String tipoToken = "Bearer";

    public JWTAuthResponseDTO(String token) {
        super();
        this.tokenAcceso = token;
    }

    public JWTAuthResponseDTO(String tokenAcceso, String tipoToken) {
        super();
        this.tokenAcceso = tokenAcceso;
        this.tipoToken = tipoToken;
    }
}
