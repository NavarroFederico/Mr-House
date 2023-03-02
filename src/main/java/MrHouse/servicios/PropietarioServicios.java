/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Propietario;
import MrHouse.enumeraciones.Roles;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.PropietarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author facuq
 */
@Service
public class PropietarioServicios implements UserDetailsService {
    
    @Autowired
    PropietarioRepositorio propietarioRepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MyException {
       
        validar (nombre, email, password, password2);
        
        Propietario propietario = new Propietario();
        propietario.setNombre(nombre);
        propietario.setEmail(email);
        propietario.setPassword(new BCryptPasswordEncoder().encode(password));
        propietario.setRol(Roles.PROPIETARIO);
        propietarioRepositorio.save(propietario);
        
        
    }
    
    @Transactional
    public void modificar(String id, String nombre, String email, String password, String password2) throws MyException {

        validar(nombre, email, password, password2);

        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propietario propietario = respuesta.get();
            propietario.setNombre(nombre);
            propietario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            propietario.setPassword(encriptada);

            propietarioRepositorio.save(propietario);
        } else {

            throw new MyException("No se encontró el usuario solicitado");
        }
    }
    
    @Transactional
    public void eliminar(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propietario propietario = respuesta.get();
            propietarioRepositorio.delete(propietario);
        }
    }
    
    private void validar(String nombre, String email, String password, String password2) throws MyException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia , ni tener menos de 6 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Propietario propietario = propietarioRepositorio.buscarPorEmail(email);

        if (propietario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + propietario.getRol().toString());

            permisos.add(p);

            return new User(propietario.getEmail(), propietario.getPassword(), permisos);

        } else {
            return null;
        }
    }
}