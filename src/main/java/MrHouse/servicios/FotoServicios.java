/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Foto;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class FotoServicios {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto save(MultipartFile file) throws MyException {
        validar(file);
        try {
            Foto foto = new Foto();
            foto.setDatos(file.getBytes());
            foto.setMime(file.getContentType());
            foto.setNombre(file.getName());
            return fotoRepositorio.save(foto);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            return null;
        }
    }

    @Transactional
    public Foto update(MultipartFile file, String idImagen) throws MyException {
        validar(file);

        try {
            Foto foto = getImage(idImagen);
            foto.setDatos(file.getBytes());
            foto.setMime(file.getContentType());
            foto.setNombre(file.getName());
            return fotoRepositorio.save(foto);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            return null;
        }
    }

    private void validar(MultipartFile file) throws MyException {
        if (null == file) {
            throw new MyException("La imágen no es válida.");
        }
    }

    @Transactional(readOnly = true)
    public Foto getImage(String id) throws MyException {
        if (null == id || id.isEmpty()) {
            throw new MyException("El ID no es válido.");
        }

        Optional<Foto> foto = fotoRepositorio.findById(id);
        if (!foto.isPresent()) {
            throw new MyException("No se ha encontrado la imágen.");
        }
        return foto.get();
    }
}
