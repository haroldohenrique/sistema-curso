package com.br.desafiorocket.desafio_rocket.modules.curso.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.desafiorocket.desafio_rocket.exceptions.CursoFoundException;
import com.br.desafiorocket.desafio_rocket.modules.curso.CursoEntity;
import com.br.desafiorocket.desafio_rocket.modules.curso.repository.CursoRepository;

@Service
public class CreateCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoEntity execute(CursoEntity cursoEntity) {
        this.cursoRepository.findByName(cursoEntity.getName())
                .ifPresent((user) -> {
                    throw new CursoFoundException(cursoEntity.getName());
                });
        return this.cursoRepository.save(cursoEntity);
    }
}
