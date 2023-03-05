/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Propiedad;
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
    public void registrar(Propiedad propiedadV) throws MyException {
        //P=PERSISTIR V=VALIDAR
        Propiedad propiedadP = validar(propiedadV);

        propiedadP.setPropiedadTipo(propiedadP.getPropiedadTipo());
        propiedadP.setM2(propiedadP.getM2());
        propiedadP.setHabitaciones(propiedadP.getHabitaciones());
        propiedadP.setBanos(propiedadP.getBanos());
        propiedadP.setCochera(propiedadP.isCochera());
        propiedadP.setDireccion(propiedadP.getDireccion());
        propiedadP.setCiudad(propiedadP.getCiudad());
        propiedadP.setDescripcion(propiedadP.getDescripcion());
        propiedadP.setInmobiliaria(propiedadP.getInmobiliaria());
        propiedadRepositorio.save(propiedadP);
    }

    //PropiedadP = propiedad persistida  PropiedadC = nuevos cambios
    @Transactional
    public void modificar(Propiedad propiedadC) throws MyException {
        try {
            Propiedad propiedadP = validarCambios(propiedadC, buscarPorId(propiedadC.getId()));

            propiedadRepositorio.save(propiedadP);
        } catch (MyException e) {
            throw new MyException("No se editó la propiedad");
        }
    }

    @Transactional
    public Propiedad buscarPorId(String id) throws MyException {
        Optional<Propiedad> op = propiedadRepositorio.findById(id);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new MyException("No se encontró la propiedad solicitada.");
        }
    }

    @Transactional
    public void bajaPropiedad(Propiedad propiedad) throws MyException {

        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            aux.setAlta(Boolean.FALSE);
            propiedadRepositorio.save(aux);
        }

    }

    @Transactional
    public void altaLibro(Propiedad propiedad) throws MyException {

        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            aux.setAlta(Boolean.TRUE);
            propiedadRepositorio.save(aux);
        }
    }

    @Transactional
    public void eliminar(Propiedad propiedad) throws MyException {
        if (propiedad.getId() == null || propiedad.getId().isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            propiedadRepositorio.delete(aux);
        }
    }

    private Propiedad validar(Propiedad propiedad) throws MyException {

        propiedad.setAlta(true);

        if (propiedad.getPropiedadTipo() <= 0 || propiedad.getPropiedadTipo() >= 3) {
            throw new MyException("El tipo de propiedad no puede ser nulo o estar vacío");
        }
        if (propiedad.getM2().isEmpty() || propiedad.getM2() == null) {
            throw new MyException("La cantidad de metros cuadrados no puede ser nula");
        }
        if (propiedad.getHabitaciones().isEmpty() || propiedad.getHabitaciones() == null) {
            throw new MyException("La cantidad de habitaciones no puede ser nula");
        }
        if (propiedad.getBanos().isEmpty() || propiedad.getBanos() == null) {
            throw new MyException("La cantidad de baños no puede ser nula");
        }
        if (propiedad.getDescripcion().isEmpty() || propiedad.getDescripcion() == null) {
            throw new MyException("La descripción no puede ser nula o estar vacía");
        }
        if (propiedad.getDireccion().isEmpty() || propiedad.getDireccion() == null) {
            throw new MyException("La dirección no puede ser nula o estar vacía");
        }
        if (propiedad.getCiudad().isEmpty() || propiedad.getCiudad() == null) {
            throw new MyException("La ciudad no puede ser nula o estar vacía");
        }
        if (propiedad.getInmobiliaria().getId().isEmpty() || propiedad.getInmobiliaria().getId() == null) {
            throw new MyException("La inmobiliaria no puede ser nula o estar vacía");
        }

        return propiedad;
    }

    //PropiedadP = propiedad persistida  PropiedadC = nuevos cambios
    private Propiedad validarCambios(Propiedad propiedadP, Propiedad propiedadC) throws MyException {

        if (propiedadC.getM2().equals(propiedadP.getM2())
                && propiedadC.getPropiedadTipo() == (propiedadP.getPropiedadTipo())
                && propiedadC.getHabitaciones().equals(propiedadP.getHabitaciones())
                && propiedadC.getBanos().equals(propiedadP.getBanos())
                && propiedadC.isCochera() == (propiedadP.isCochera())
                && propiedadC.getDireccion().equals(propiedadP.getDireccion())
                && propiedadC.getCiudad().equals(propiedadP.getCiudad())
                && propiedadC.getDescripcion().equals(propiedadP.getDescripcion())
                && propiedadC.getFoto().equals(propiedadP.getFoto())
                && propiedadC.getInmobiliaria().equals(propiedadP.getInmobiliaria())
                && propiedadC.getPropietario().equals(propiedadP.getPropietario())) {
            throw new MyException("No existen cambios para editar");
        }

        if (propiedadC.getPropiedadTipo() != (propiedadP.getPropiedadTipo())) {
            propiedadP.setPropiedadTipo(propiedadC.getPropiedadTipo());
        }
        if (!propiedadC.getM2().equals(propiedadP.getM2())) {
            propiedadP.setHabitaciones(propiedadC.getHabitaciones());
        }
        if (!propiedadC.getHabitaciones().equals(propiedadP.getHabitaciones())) {
            propiedadP.setHabitaciones(propiedadC.getHabitaciones());
        }
        if (!propiedadC.getBanos().equals(propiedadP.getBanos())) {
            propiedadP.setBanos(propiedadC.getBanos());
        }
        if (!propiedadC.isCochera() == (propiedadP.isCochera())) {
            propiedadP.setCochera(propiedadC.isCochera());
        }
        if (!propiedadC.getDireccion().equals(propiedadP.getDireccion())) {
            propiedadP.setDireccion(propiedadC.getDireccion());
        }
        if (!propiedadC.getCiudad().equals(propiedadP.getCiudad())) {
            propiedadP.setCiudad(propiedadC.getCiudad());
        }
        if (!propiedadC.getDescripcion().equals(propiedadP.getDescripcion())) {
            propiedadP.setDescripcion(propiedadC.getDescripcion());
        }
        if (!propiedadC.getFoto().equals(propiedadP.getFoto())) {
            propiedadP.setFoto(propiedadC.getFoto());
        }
        if (!propiedadC.getInmobiliaria().equals(propiedadP.getInmobiliaria())) {
            propiedadP.setInmobiliaria(propiedadC.getInmobiliaria());
        }
        if (!propiedadC.getPropietario().equals(propiedadP.getPropietario())) {
            propiedadP.setPropietario(propiedadC.getPropietario());
        }
        return propiedadP;
    }
}
