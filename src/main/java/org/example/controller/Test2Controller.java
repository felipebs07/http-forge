package org.example.controller;

import org.example.annotation.rest.Controller;
import org.example.annotation.rest.RequestMapping;

@Controller(path = "/test2")
public class Test2Controller {

    @RequestMapping
    public String getUsers() {
        return "Lista de usuários2";
    }

    @RequestMapping(method = "POST")
    public String create() {
        return "Usuário2 criado com sucesso";
    }

    @RequestMapping(method = "PUT")
    public String update() {
        return "Usuário2 atualizado com sucesso";
    }

    @RequestMapping(method = "DELETE")
    public String delete() {
        return "Usuário2 deletado com sucesso!";
    }

}
