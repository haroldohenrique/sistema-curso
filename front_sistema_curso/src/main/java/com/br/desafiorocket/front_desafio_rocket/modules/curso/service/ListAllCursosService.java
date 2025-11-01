package com.br.desafiorocket.front_desafio_rocket.modules.curso.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.br.desafiorocket.front_desafio_rocket.modules.curso.dto.CursoDTO;

@Service
public class ListAllCursosService {

    @Value("${url.api.cursos}")
    private String hostAPICursos;

    public List<CursoDTO> execute() {
        RestTemplate rt = new RestTemplate();
        String url = hostAPICursos.concat("/curso/all");

        try {
            ResponseEntity<CursoDTO[]> response = rt.exchange(url, HttpMethod.GET, null, CursoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            // Nenhum curso cadastrado → devolve lista vazia
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cursos: " + e.getMessage());
        }
    }
}
