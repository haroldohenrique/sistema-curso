package com.br.desafiorocket.desafio_rocket.modules.curso.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.desafiorocket.desafio_rocket.exceptions.CursoFoundException;
import com.br.desafiorocket.desafio_rocket.modules.curso.CursoEntity;
import com.br.desafiorocket.desafio_rocket.modules.curso.dto.CursoDTO;
import com.br.desafiorocket.desafio_rocket.modules.curso.repository.CursoRepository;
import com.br.desafiorocket.desafio_rocket.modules.curso.useCase.CreateCursoUseCase;
import com.br.desafiorocket.desafio_rocket.modules.curso.useCase.SearchCursoUseCase;
import com.br.desafiorocket.desafio_rocket.modules.curso.useCase.UpdateCursoUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private UpdateCursoUseCase updateCursoUseCase;

    @Autowired
    private CreateCursoUseCase createCursoUseCase;

    @Autowired
    private SearchCursoUseCase searchCursoUseCase;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CursoEntity cursoEntity) {
        try {
            var result = this.createCursoUseCase.execute(cursoEntity);
            return ResponseEntity.ok().body(result);

        } catch (CursoFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> get(@RequestParam UUID cursoId) {
        {
            try {
                var cursoOptional = this.cursoRepository.findById(cursoId);
                if (cursoOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
                }
                var curso = cursoOptional.get();

                return ResponseEntity.ok().body(curso);

            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllCursos() {
        try {
            var cursos = cursoRepository.findAll();
            if (cursos.isEmpty()) {
                throw new UsernameNotFoundException("Não existe nenhum curso cadastrado.");
            }
            return ResponseEntity.ok(cursos);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @GetMapping("/search")
    public ResponseEntity<Object> getSearchCurso(@RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {

        try {
            var curso = searchCursoUseCase.execute(name, category);
            return ResponseEntity.ok(curso);

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<Object> put(@RequestParam UUID id, @RequestBody CursoDTO cursoDTO) {
        try {
            var result = this.updateCursoUseCase.execute(id, cursoDTO);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> delete(@RequestParam UUID id) {
        try {
            var curso = cursoRepository.findById(id)
                    .orElseThrow(() -> {
                        throw new UsernameNotFoundException("Curso não encontrado.");
                    });
            cursoRepository.delete(curso);
            return ResponseEntity.ok().body("Curso deletado com sucesso.");

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Object> patch(@PathVariable UUID id) {
        try {
            var curso = cursoRepository.findById(id)
                    .orElseThrow(() -> {
                        throw new UsernameNotFoundException("Curso não encontrado.");
                    });

            curso.setActive(!curso.isActive());
            String status = curso.isActive() ? "está ativo" : "não está ativo";
            cursoRepository.save(curso);
            return ResponseEntity.ok().body("O curso de " + curso.getName() + " " + status);

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
