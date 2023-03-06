/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Propiedad;
import MrHouse.excepciones.MyException;
import MrHouse.servicios.PropiedadServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import MrHouse.servicios.PropiedadServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/propiedades")
public class PropiedadControlador {

    @GetMapping("/")
    public String propiedades() {
        return "propiedades.html";
    }

    @GetMapping("/registrar")
    public String registro(ModelMap modelo, @RequestParam(required = false) String id) {

        return "registro.html";
    }
}
