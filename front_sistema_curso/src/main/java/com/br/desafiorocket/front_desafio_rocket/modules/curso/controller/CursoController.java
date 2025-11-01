package com.br.desafiorocket.front_desafio_rocket.modules.curso.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.desafiorocket.front_desafio_rocket.modules.curso.dto.CursoDTO;
import com.br.desafiorocket.front_desafio_rocket.modules.curso.service.CreateCursoService;
import com.br.desafiorocket.front_desafio_rocket.modules.curso.service.DeleteCursoService;
import com.br.desafiorocket.front_desafio_rocket.modules.curso.service.GetCursoService;
import com.br.desafiorocket.front_desafio_rocket.modules.curso.service.ListAllCursosService;
import com.br.desafiorocket.front_desafio_rocket.modules.curso.service.UpdateCursoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private DeleteCursoService deleteCursoService;

    @Autowired
    private ListAllCursosService listAllCursosService;

    @Autowired
    private GetCursoService getCursoService;

    @Autowired
    private CreateCursoService createCursoService;

    @Autowired
    private UpdateCursoService updateCursoService;

    // TODO list
    @GetMapping("/list")
    public String getList(Model model) {
        var result = this.listAllCursosService.execute();

        if (result.isEmpty()) {
            return "redirect:/curso/create";
        }

        model.addAttribute("cursos", result);

        return "curso/list";
    }

    // TODO create
    // TODO tratar exception de cursofound quando tem nomes iguais
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("cursoDTO", new CursoDTO());
        return "curso/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("cursoDTO") CursoDTO cursoDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("cursoDTO", cursoDTO);
            return "curso/create";
        }

        try {
            UUID cursoId = this.createCursoService.execute(cursoDTO);
            return "redirect:/curso/detail?cursoId=" + cursoId;
        } catch (Exception ex) {
            model.addAttribute("cursoDTO", cursoDTO);
            model.addAttribute("error_message", "Erro ao cadastrar o curso.");
            return "curso/create";
        }
    }

    // TODO detail
    @GetMapping("/detail")
    public String detail(@RequestParam(required = false) UUID cursoId, Model model) {

        // Se a requisição não tiver ID (alguém acessou /curso/detail sem parâmetro)
        if (cursoId == null) {
            return "redirect:/curso/list";
        }

        // Chama o serviço GET do Front-end
        var cursoResponse = this.getCursoService.execute(cursoId);

        // Verifica se o curso não foi encontrado (Status 404 do GetCursoService)
        if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
            return "redirect:/curso/list?error=notfound";
        }

        // Carrega o curso no Model e renderiza a página de detalhes
        model.addAttribute("curso", cursoResponse.getBody());
        return "curso/detail";
    }

    // TODO edit

    @GetMapping("/edit")
    public String editGet(@RequestParam(required = false) UUID cursoId, Model model) {
        if (cursoId == null) {
            return "redirect:/curso/list";
        }
        var cursoResponse = this.getCursoService.execute(cursoId);
        if (cursoResponse.getStatusCode() == HttpStatus.NOT_FOUND || cursoResponse.getBody() == null) {
            return "redirect:/curso/list?error=notfound";
        }

        // Se a requisição não tiver ID (alguém acessou /curso/detail sem parâmetro)
        model.addAttribute("cursoDTO", cursoResponse.getBody());
        model.addAttribute("cursoId", cursoId);

        return "curso/edit";
    }

    @PostMapping("/edit")
    public String editSave(@RequestParam UUID cursoId, @Valid @ModelAttribute("cursoDTO") CursoDTO cursoDTO,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cursoDTO", cursoDTO);
            model.addAttribute("cursoId", cursoId); // Garante que o ID ainda esteja no modelo
            return "curso/edit";
        }
        try {
            this.updateCursoService.execute(cursoId, cursoDTO);
            return "redirect:/curso/detail?cursoId=" + cursoId;
        } catch (Exception ex) {
            model.addAttribute("cursoDTO", cursoDTO);
            model.addAttribute("cursoId", cursoId);
            model.addAttribute("error_message", "Erro ao atualizar o curso: " + ex.getMessage());
            return "curso/edit";
        }

    }

    // TODO delete

    @PostMapping("/delete")
    public String delete(@RequestParam(required = false) UUID cursoId, Model model) {
        if (cursoId == null) {
            return "redirect:/curso/list";
        }

        try {
            this.deleteCursoService.execute(cursoId);
            return "redirect:/curso/list?success=deleted";

        } catch (Exception ex) {
            // Se falhar (ex: curso não encontrado), redireciona de volta para os detalhes
            // com uma mensagem de erro.
            model.addAttribute("error_message", "Erro ao excluir o curso: " + ex.getMessage());
            // Para mostrar o erro, é melhor retornar para a página de detalhes, mas é mais
            // simples
            // redirecionar para a lista com um erro genérico se o detalhe não puder mais
            // ser carregado.
            // Vou manter o redirecionamento para a lista com uma flag de erro.
            return "redirect:/curso/list?error=deletion_failed";
        }
    }
}
