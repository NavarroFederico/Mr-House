/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;


import MrHouse.entidades.Inmobiliaria;
import MrHouse.enumeraciones.Roles;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.InmobiliariaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class InmobiliariaServicios implements UserDetailsService {

    @Autowired
    private InmobiliariaRepositorio inmobiliariaRepositorio;

    @Autowired
    private ClienteServicios clienteServicios;

    @Autowired
    private FotoServicios fotoServicios;

    @javax.transaction.Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MyException {

        validar(nombre, email, password, password2);

        Inmobiliaria inmobiliaria = new Inmobiliaria();
        inmobiliaria.setNombre(nombre);
        inmobiliaria.setEmail(email);
        inmobiliaria.setPassword(new BCryptPasswordEncoder().encode(password));
        inmobiliaria.setRol(Roles.PROPIETARIO);
        inmobiliariaRepositorio.save(inmobiliaria);

    }

    @javax.transaction.Transactional
    public void modificar(String id, String nombre, String email, String password, String password2) throws MyException {

        validar(nombre, email, password, password2);

        Optional<Inmobiliaria> respuesta = inmobiliariaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Inmobiliaria inmobiliaria = respuesta.get();
            inmobiliaria.setNombre(nombre);
            inmobiliaria.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            inmobiliaria.setPassword(encriptada);

            inmobiliariaRepositorio.save(inmobiliaria);
        } else {

            throw new MyException("No se encontró el usuario solicitado");
        }
    }

    @javax.transaction.Transactional
    public void eliminar(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Inmobiliaria> respuesta = inmobiliariaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Inmobiliaria inmobiliaria = respuesta.get();
            inmobiliariaRepositorio.delete(inmobiliaria);
        }
    }

    private void validar(String title, String body, String photo, String password2) throws MyException {
        if (null == title || title.isEmpty()) {
            throw new MyException("El título ingresado no es válido.");
        }
        if (null == body || body.isEmpty()) {
            throw new MyException("La descripción no es válida.");
        }
        if (null == photo || photo.isEmpty()) {
            throw new MyException("La foto no es válida.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Inmobiliaria inmobiliaria = inmobiliariaRepositorio.buscarPorID(id);

        if (inmobiliaria != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + inmobiliaria.getRol().toString());

            permisos.add(p);

            return new User(inmobiliaria.getId(), inmobiliaria.getPassword(), permisos);

        } else {
            return null;
        }
    }
}
