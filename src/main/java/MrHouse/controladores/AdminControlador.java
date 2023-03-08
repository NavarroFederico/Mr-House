/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Cliente;
import MrHouse.excepciones.MyException;
import MrHouse.servicios.ClienteServicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Cliente> clientes = clienteServicios.listarClientes();
        modelo.addAttribute("clientes", clientes);
        return "usuarios.html";
    }
    

    @GetMapping("/editarCliente/{id}")
    public String editar(@PathVariable String id , ModelMap modelo) {
        modelo.put("Cliente", clienteServicios.getOne(id));
        return "usuarios.html";
    }
    @PostMapping("/editarCliente/{id}")
    public String modificar(@PathVariable String id , String nombre ,  String email , String password,String password2, ModelMap modelo){
        try {
            clienteServicios.modificar(id, nombre, email, password, password2);
            return "redirect:../usuarios";
        } catch (MyException ex) {
            modelo.put("error!", ex.getMessage());
            return "usuarios.html";
        }
    }

    @GetMapping("/eliminarCliente/{id}")
    public String eliminar(@PathVariable String id ,ModelMap modelo) {
        try {
            clienteServicios.eliminarCliente(id);
            return "redirect:../usuarios";
        } catch (MyException ex) {
            modelo.put("error!", ex.getMessage());
        }
        return "usuarios.html";
    }
}
