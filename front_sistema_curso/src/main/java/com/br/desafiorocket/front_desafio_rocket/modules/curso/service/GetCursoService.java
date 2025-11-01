package com.br.desafiorocket.front_desafio_rocket.modules.curso.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.desafiorocket.front_desafio_rocket.modules.curso.dto.CursoDTO;

@Service
public class GetCursoService {

    @Value("${url.api.cursos}")
    private String hostAPICursos;

    public ResponseEntity<CursoDTO> execute(UUID cursoId) {
        RestTemplate rt = new RestTemplate();
        String url = hostAPICursos.concat("/curso/?cursoId=" + cursoId);
        ResponseEntity<CursoDTO> result = rt.getForEntity(url, CursoDTO.class);
        return result   ;
    }
}
