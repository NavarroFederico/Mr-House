/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Cliente;
import MrHouse.servicios.ClienteServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/imagen")
public class FotoControlador {

    @Autowired
    private ClienteServicios clienteServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> clienteImagen(@PathVariable String id) {
        Cliente cliente = clienteServicio.getOne(id);

        byte[] foto = cliente.getImage().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity(foto, headers, HttpStatus.OK);
    }

}
