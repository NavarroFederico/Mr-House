/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Cliente;
import MrHouse.entidades.Foto;
import MrHouse.repositorios.ClienteRepositorio;
import MrHouse.repositorios.FotoRepositorio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/imagen")
public class FotoControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> clienteImagen(@PathVariable String id) {
        Cliente cliente = clienteRepositorio.buscarPorID(id);

        byte[] foto = cliente.getImage().getDatos();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity(foto, headers, HttpStatus.OK);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFoto(@PathVariable String id) {
        Foto res = fotoRepositorio.searchById(id);

        byte[] foto = res.getDatos();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity(foto, headers, HttpStatus.OK);

    }
}
