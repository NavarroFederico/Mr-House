/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Inmobiliaria;
import MrHouse.entidades.Propiedad;
import MrHouse.entidades.Propietario;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.PropiedadRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author facuq
 */
public class PropiedadServicios {
     @Autowired
    PropiedadRepositorio propiedadRepositorio;
    
    @Transactional
    public void registrar(String m2, String habitaciones, String banos, String descripcion, String direccion, String ciudad, Inmobiliaria inmobiliaria, Propietario propietario) throws MyException {
       
        validar (m2, habitaciones, banos, descripcion, direccion, ciudad, inmobiliaria, propietario);
        
        Propiedad propiedad = new Propiedad();
        propiedad.setM2(m2);
        propiedad.setHabitaciones(habitaciones);
        propiedad.setBanos(banos);
        propiedad.setDescripcion(descripcion);
        propiedad.setDireccion(direccion);
        propiedad.setCiudad(ciudad);
        propiedad.setInmobiliaria(inmobiliaria);
        propiedad.setPropietario(propietario);
        propiedadRepositorio.save(propiedad);
    }
    
    @Transactional
    public void modificar(String id, String m2, String habitaciones, String banos, String descripcion, String direccion, String ciudad, Inmobiliaria inmobiliaria, Propietario propietario) throws MyException {

        validar (m2, habitaciones, banos, descripcion, direccion, ciudad, inmobiliaria, propietario);

        Optional<Propiedad> respuesta = propiedadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedad.setM2(m2);
            propiedad.setHabitaciones(habitaciones);
            propiedad.setBanos(banos);
            propiedad.setDescripcion(descripcion);
            propiedad.setDireccion(direccion);
            propiedad.setCiudad(ciudad);
            propiedad.setInmobiliaria(inmobiliaria);
            propiedad.setPropietario(propietario);
            propiedadRepositorio.save(propiedad);
        } else {

            throw new MyException("No se encontró la propiedad solicitada");
        }
    }
    
    @Transactional
    public void eliminar(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Propiedad> respuesta = propiedadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedadRepositorio.delete(propiedad);
        }
    }
    
    private void validar(String m2, String habitaciones, String banos, String descripcion, String direccion, String ciudad, Inmobiliaria inmobiliaria, Propietario propietario) throws MyException {
        if (m2.isEmpty() || m2 == null) {
            throw new MyException("La cantidad de metros cuadrados no puede ser nula");
        }
        if (habitaciones.isEmpty() || habitaciones == null) {
            throw new MyException("La cantidad de habitaciones no puede ser nula");
        }
        if (banos.isEmpty() || banos == null) {
            throw new MyException("La cantidad de baños no puede ser nula");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new MyException("La descripción no puede ser nula o estar vacía");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new MyException("La dirección no puede ser nula o estar vacía");
        }
        if (ciudad.isEmpty() || ciudad == null) {
            throw new MyException("La ciudad no puede ser nula o estar vacía");
        }
        if (inmobiliaria.getId().isEmpty() || inmobiliaria.getId() == null) {
            throw new MyException("La inmobiliaria no puede ser nula o estar vacía");
        }
    }
}
