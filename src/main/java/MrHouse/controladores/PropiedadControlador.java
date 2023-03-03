/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author facuq
 */
public class PropiedadControlador {

    @Autowired

    @GetMapping("/alquileres")
    public String alquileres() {
        return "alquileres.html";
    }

    @GetMapping("/ventas")
    public String ventas() {
        return "ventas.html";
    }

    @GetMapping("/alquileres/casas")
    public String aCasas() {
        return "casas-alquiler.html";
    }

    @GetMapping("/ventas/casas")
    public String vCasas() {
        return "casas-venta.html";
    }

    @GetMapping("/alquileres/departamentos")
    public String aDepartamentos() {
        return "departamentos-alquiler.html";
    }

    @GetMapping("/ventas/departamentos")
    public String vDepartamentos() {
        return "departamentos-ventas.html";
    }
}
