package com.br.desafiorocket.desafio_rocket.exceptions;

public class CursoFoundException extends RuntimeException {

    private String name;

    public CursoFoundException(String name) {
        super("O curso selecionado já existe.");
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
