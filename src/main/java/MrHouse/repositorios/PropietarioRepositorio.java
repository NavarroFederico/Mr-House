/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;


import MrHouse.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author facuq
 */
public interface PropietarioRepositorio extends  JpaRepository<Propietario, String> {
    @Query("SELECT u FROM Cliente u WHERE u.email = :email")
    
    public Propietario buscarPorEmail(@Param("email") String email); 
}
