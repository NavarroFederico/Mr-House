/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Foto;
import MrHouse.repositorios.FotoRepositorio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Controller
public class FotoControlador {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    @PostMapping("/usuarios/{usuarioId}/foto")
    public String subirFotoPerfil(@PathVariable Long usuarioId, @RequestParam("archivo") MultipartFile archivo) {
        try {
            byte[] datos = archivo.getBytes();
            String nombre = archivo.getOriginalFilename();
            
            Foto foto = new Foto();
            foto.setNombre(nombre);
            foto.setDatos(datos);
            
            fotoRepositorio.save(foto);
            
            // Guardar la asociación entre el usuario y la foto en la base de datos
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "redirect:/perfil";
    }
}