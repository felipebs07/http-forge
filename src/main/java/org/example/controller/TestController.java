package org.example.controller;

import org.example.annotation.rest.RequestMapping;

public class TestController {

    @RequestMapping(path = "/test")
    public String getUsers() {
        return "Lista de usuários";
    }

    @RequestMapping(path = "/test", method = "POST")
    public String create() {
        return "Usuário criado com sucesso";
    }

    @RequestMapping(path = "/test", method = "PUT")
    public String update() {
        return "Usuário atualizado com sucesso";
    }

    @RequestMapping(path = "/test", method = "DELETE")
    public String delete() {
        return "Usuário deletado com sucesso!";
    }

}
