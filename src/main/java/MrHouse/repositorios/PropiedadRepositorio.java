/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;

import MrHouse.entidades.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author facuq
 */
@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, String> {
    
    @Query("SELECT p FROM Propiedad p WHERE p.direccion = :direccion")
    
    public Propiedad buscarPorDireccion(@Param("direccion") String direccion);
    
}
