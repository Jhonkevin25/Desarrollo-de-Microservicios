package com.jhon.cursos.curso;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Información básica
    private String nombre;
    private String descripcion;
    private String categoria;     // Ej: Programación, Diseño, Marketing

    // Duración en horas
    private Integer duracionHoras;

    // Precio del curso
    private BigDecimal precio;

    // Estado
    private Boolean activo;

    // Fechas
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Nivel: BASICO, INTERMEDIO, AVANZADO
    private String nivel;

    // Capacidad máxima de estudiantes
    private Integer capacidadMaxima;
}