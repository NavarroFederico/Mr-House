/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;

import MrHouse.entidades.Propiedad;
import MrHouse.entidades.Propietario;
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
}
