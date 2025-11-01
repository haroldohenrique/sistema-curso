package com.br.desafiorocket.front_desafio_rocket.modules.curso.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class DeleteCursoService {

    @Value("${url.api.cursos}")
    private String hostAPICursos;

    public void execute(UUID cursoId) {
        RestTemplate rt = new RestTemplate();

        String url = hostAPICursos.concat("/curso/?id=" + cursoId);

        rt.exchange(url, HttpMethod.DELETE, null, Void.class);
    }
}
