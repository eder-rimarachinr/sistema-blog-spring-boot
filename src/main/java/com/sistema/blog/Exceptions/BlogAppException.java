package com.sistema.blog.Exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogAppException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String mensaje;

    public BlogAppException(HttpStatus status, String mensaje) {
        super();
        this.status = status;
        this.mensaje = mensaje;
    }

    public BlogAppException(HttpStatus status, String message, String mensaje) {
        super(message);
        this.status = status;
        this.mensaje = message;
    }
}
