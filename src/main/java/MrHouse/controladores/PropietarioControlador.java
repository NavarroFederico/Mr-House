/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.excepciones.MyException;
import MrHouse.servicios.PropietarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author facuq
 */

@Controller
@RequestMapping("/propietario")
public class PropietarioControlador {
    
    @Autowired
    private PropietarioServicios propietarioServicios;
     
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre , @RequestParam String email , @RequestParam String password ,
            @RequestParam String password2 , ModelMap modelo ) {
            try {
            propietarioServicios.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");
            return "index.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("email",email);
            return "registro.html";
        }
    }
    
    @GetMapping("/ingresar")
    public String login(@RequestParam(required = false)String error , ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidas");
        }
        return "ingreso.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_INQUILINO','ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_INMOBILIARIA')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }
    
}
