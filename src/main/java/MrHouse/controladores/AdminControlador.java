/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Cliente;
import MrHouse.servicios.ClienteServicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private ClienteServicios clienteServicios;
    
    @GetMapping("/dashboard")
    public String panelAdmin() {
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Cliente> clientes = clienteServicios.listarClientes();
        modelo.addAttribute("clientes" , clientes);
        return "usuarios.html";
    }
    
    @GetMapping("/editarCliente{id}") 
    public String editar(@PathVariable String id , ModelMap modelo) {
        modelo.put("Cliente", clienteServicios.getOne(id));
        return "editarCliente.html";
    }
}
