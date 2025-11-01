package com.br.desafiorocket.front_desafio_rocket.modules.curso.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.desafiorocket.front_desafio_rocket.modules.curso.dto.CursoDTO;

@Service
public class UpdateCursoService {

    @Value("${url.api.cursos}")
    private String hostAPICursos;

    public CursoDTO execute(UUID cursoId, CursoDTO cursoDTO) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new HttpEntity<>(cursoDTO, headers);

        String url = hostAPICursos.concat("/curso/?id=" + cursoId);
        var result = rt.exchange(url, HttpMethod.PUT,
                request, CursoDTO.class);

        return result.getBody();
    }
}
