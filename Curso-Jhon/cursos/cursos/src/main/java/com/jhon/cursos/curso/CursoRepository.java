package com.jhon.cursos.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio de Curso.
 * Extiende JpaRepository (en lugar de CrudRepository) para obtener
 * soporte nativo de paginación y ordenamiento con Page<T> y Pageable.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Buscar todos — heredado de JpaRepository (List<T> findAll())

    // Buscar por categoría
    List<Curso> findByCategoria(String categoria);

    // Buscar por nivel
    List<Curso> findByNivel(String nivel);

    // Buscar solo los activos
    List<Curso> findByActivo(Boolean activo);

    // Paginación filtrada por categoría
    Page<Curso> findByCategoria(String categoria, Pageable pageable);

    // Paginación filtrada por activo
    Page<Curso> findByActivo(Boolean activo, Pageable pageable);

    // Búsqueda por nombre (contiene, ignorando mayúsculas)
    @Query("SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Curso> findByNombreContaining(@Param("nombre") String nombre, Pageable pageable);
}