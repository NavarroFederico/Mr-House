/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;

import MrHouse.entidades.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author facuq
 */
public interface PropiedadRepositorio extends  JpaRepository<Propiedad, String> {
    @Query("SELECT u FROM Propiedad u WHERE u.id = :id")
    
    public Propiedad buscarPorID(@Param("id") String id);
    
    @Query("SELECT u FROM Propiedad u WHERE u.ciudad = :ciudad")
    
    List<Propiedad> buscarPorCiudad(@Param("ciudad") String ciudad);
    
    @Query("SELECT u FROM Propiedad u WHERE u.ciudad = :ciudad")
    
    public Propiedad buscarPorAlta(@Param("ciudad") String ciudad);
    
    @Query("SELECT p from Propiedad p WHERE p.alta = true")
    List<Propiedad> buscaActivos(@Param("activos") String activos);
}
