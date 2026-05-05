package com.jhon.cursos.curso;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para Curso.
 * Se usa para controlar qué datos se exponen en las respuestas de la API,
 * separando la capa de presentación de la entidad de base de datos.
 */
@Data
public class CursoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer duracionHoras;
    private BigDecimal precio;
    private Boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String nivel;
    private Integer capacidadMaxima;

    /**
     * Convierte una entidad Curso en un CursoDTO.
     */
    public static CursoDTO fromEntity(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        dto.setCategoria(curso.getCategoria());
        dto.setDuracionHoras(curso.getDuracionHoras());
        dto.setPrecio(curso.getPrecio());
        dto.setActivo(curso.getActivo());
        dto.setFechaInicio(curso.getFechaInicio());
        dto.setFechaFin(curso.getFechaFin());
        dto.setNivel(curso.getNivel());
        dto.setCapacidadMaxima(curso.getCapacidadMaxima());
        return dto;
    }

    /**
     * Convierte este DTO en una entidad Curso.
     */
    public Curso toEntity() {
        Curso curso = new Curso();
        curso.setId(this.id);
        curso.setNombre(this.nombre);
        curso.setDescripcion(this.descripcion);
        curso.setCategoria(this.categoria);
        curso.setDuracionHoras(this.duracionHoras);
        curso.setPrecio(this.precio);
        curso.setActivo(this.activo);
        curso.setFechaInicio(this.fechaInicio);
        curso.setFechaFin(this.fechaFin);
        curso.setNivel(this.nivel);
        curso.setCapacidadMaxima(this.capacidadMaxima);
        return curso;
    }
}