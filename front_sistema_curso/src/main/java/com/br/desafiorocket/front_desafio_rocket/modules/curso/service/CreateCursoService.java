package com.br.desafiorocket.front_desafio_rocket.modules.curso.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.desafiorocket.front_desafio_rocket.modules.curso.dto.CursoDTO;

@Service
public class CreateCursoService {

    @Value("${url.api.cursos}")
    private String hostAPICursos;

    public UUID execute(CursoDTO cursoDTO) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CursoDTO> request = new HttpEntity<>(cursoDTO, headers);

        String url = hostAPICursos.concat("/curso/");

        // Esperar um objeto CursoDTO como resposta (presumindo que o Back-end o retorne)
        // Isso é crucial para que o ID seja deserializado.
        ResponseEntity<CursoDTO> result = rt.postForEntity(url, request, CursoDTO.class);
        System.out.println("AQUI É O RESULT" + result);

        //CAPTURA: Verificar se a resposta foi um sucesso e se o corpo contém o ID
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
             return result.getBody().getId(); // Retorna o ID
        }

        // Se o Back-end não retornar 2xx, o RestTemplate lança uma exceção,
        // mas se a resposta não for tratada, lançamos uma exceção genérica
        throw new RuntimeException("Falha ao criar curso. Status: " + result.getStatusCode());
    }
}
