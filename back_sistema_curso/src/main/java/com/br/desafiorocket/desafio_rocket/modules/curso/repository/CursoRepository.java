package com.br.desafiorocket.desafio_rocket.modules.curso.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.desafiorocket.desafio_rocket.modules.curso.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {

    List<CursoEntity> findAllByNameAndCategory(String name, String category);
    List<CursoEntity> findAllByName(String name);
    Optional<CursoEntity> findByName(String name);
}
