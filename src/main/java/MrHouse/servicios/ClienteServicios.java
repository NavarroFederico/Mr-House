/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Cliente;
import MrHouse.entidades.Foto;
import MrHouse.enumeraciones.Roles;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class ClienteServicios implements UserDetailsService {

    @Autowired
    ClienteRepositorio clienteRepositorio;
    @Autowired
    FotoServicios fotoservicios;

    //Crear Cliente
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MyException {

        validar(nombre, email, password, password2);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setRol(Roles.INQUILINO);
        Foto foto = fotoservicios.save(archivo);
        cliente.setImage(foto);

        clienteRepositorio.save(cliente);
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

    @Transactional
    public void modificar(MultipartFile archivo,String id, String nombre, String email, String password, String password2) throws MyException {

        validar(nombre, email, password, password2);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            cliente.setPassword(encriptada);
            String idImagen = null;
            if(cliente.getImage() != null){
                idImagen = cliente.getImage().getId();
            }
            Foto foto = fotoservicios.update(archivo, idImagen);
            cliente.setImage(foto);

            clienteRepositorio.save(cliente);
        } else {
            throw new MyException("No se encontró el usuario solicitado");
        }
    }
    
     //Metodo para listar clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList();
        clientes = clienteRepositorio.findAll();
        return clientes;
    }
    
     //Metodo GetOne para editar
    public Cliente getOne(String id) {
        return clienteRepositorio.getOne(id);
    }
   
    @Transactional
    public void eliminarCliente(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            clienteRepositorio.delete(cliente);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.buscarPorEmail(email);

        if (cliente != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + cliente.getRol().toString());

            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            
            
            return new User(cliente.getEmail(), cliente.getPassword(), permisos);

        } else {
            return null;
        }
    }

}
