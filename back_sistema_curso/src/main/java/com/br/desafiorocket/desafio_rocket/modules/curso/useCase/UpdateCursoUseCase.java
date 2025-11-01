package com.br.desafiorocket.desafio_rocket.modules.curso.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.desafiorocket.desafio_rocket.modules.curso.CursoEntity;
import com.br.desafiorocket.desafio_rocket.modules.curso.dto.CursoDTO;
import com.br.desafiorocket.desafio_rocket.modules.curso.repository.CursoRepository;

@Service
public class UpdateCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoEntity execute(UUID id, CursoDTO cursoDTO) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> {
                    // Use a exceção padrão para NOT_FOUND
                    throw new UsernameNotFoundException("Curso não encontrado");
                });

        if (cursoDTO.getName() != null && !cursoDTO.getName().isBlank()) {
            curso.setName(cursoDTO.getName());
        }

        if (cursoDTO.getCategory() != null && !cursoDTO.getCategory().isBlank()) {
            curso.setCategory(cursoDTO.getCategory());
        }

        if (cursoDTO.getProfessor() != null && !cursoDTO.getProfessor().isBlank()) {
            curso.setProfessor(cursoDTO.getProfessor());
        }

        if (cursoDTO.getActive() != null) {
            curso.setActive(cursoDTO.getActive());
        }

        // Verificação mínima para garantir que pelo menos um campo foi fornecido
        if ((cursoDTO.getName() == null || cursoDTO.getName().isBlank()) &&
                (cursoDTO.getCategory() == null || cursoDTO.getCategory().isBlank()) &&
                (cursoDTO.getProfessor() == null || cursoDTO.getProfessor().isBlank()) &&
                (cursoDTO.getActive() == null)) {

            throw new IllegalArgumentException("Nenhum campo válido para atualização foi fornecido.");
        }

        return this.cursoRepository.save(curso);
    }
}