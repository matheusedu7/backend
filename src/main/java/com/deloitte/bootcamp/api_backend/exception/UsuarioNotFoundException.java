package com.deloitte.bootcamp.api_backend.exception;

    public class UsuarioNotFoundException extends RuntimeException {
        public UsuarioNotFoundException(String message) {
            super(message);
        }
    }
