package com.br.desafiorocket.desafio_rocket.modules.curso.useCase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.desafiorocket.desafio_rocket.modules.curso.CursoEntity;
import com.br.desafiorocket.desafio_rocket.modules.curso.repository.CursoRepository;


@Service
public class SearchCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoEntity> execute(String name, String category) {
        List<CursoEntity> cursos;

        if (name != null && !name.isBlank() && category != null && !category.isBlank()) {
            cursos = cursoRepository.findAllByNameAndCategory(name, category);
        } else if (name != null && !name.isBlank()) {
            cursos = cursoRepository.findAllByName(name);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos, informe pelo menos um nome.");
        }
        if (cursos.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return cursos;
    }
}
