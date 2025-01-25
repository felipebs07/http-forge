package org.example.controller;

import org.example.annotation.rest.Controller;
import org.example.annotation.rest.RequestMapping;

@Controller(path = "/test")
public class TestController {

    @RequestMapping
    public String getUsers() {
        return "Lista de usuários";
    }

    @RequestMapping(method = "POST")
    public String create() {
        return "Usuário criado com sucesso";
    }

    @RequestMapping(method = "PUT")
    public String update() {
        return "Usuário atualizado com sucesso";
    }

    @RequestMapping(method = "DELETE")
    public String delete() {
        return "Usuário deletado com sucesso!";
    }

}
