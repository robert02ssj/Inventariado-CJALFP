package com.cjalfp.inventario.repository;

import com.cjalfp.inventario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Obtener usuarios que NO tienen equipos asignados actualmente
    @Query("SELECT u FROM Usuario u WHERE u.id NOT IN " +
           "(SELECT DISTINCT i.usuario.id FROM Inventario i WHERE i.fechaDevolucion IS NULL) " +
           "ORDER BY u.nombre, u.apellidos")
    List<Usuario> findUsuariosSinEquipos();
}
