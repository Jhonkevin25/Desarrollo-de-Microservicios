package com.jhon.cursos.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso Curso.
 *
 * Base URL: /cursos
 *
 * Endpoints disponibles:
 *  GET    /cursos                  → Listar todos (sin paginar)
 *  GET    /cursos/paginado         → Listar con paginación y ordenamiento
 *  GET    /cursos/buscar?nombre=X  → Buscar por nombre (paginado)
 *  GET    /cursos/{id}             → Obtener por ID
 *  POST   /cursos                  → Crear nuevo
 *  PUT    /cursos/{id}             → Actualizar completo
 *  PATCH  /cursos/{id}             → Actualizar parcial
 *  DELETE /cursos/{id}             → Eliminar
 */
@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    // ─────────────────────────────────────────────────────────────────
    // GET ALL — lista todos sin paginación
    // ─────────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<CursoDTO>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    // ─────────────────────────────────────────────────────────────────
    // GET ALL PAGINADO
    // Parámetros de query:
    //   page   → número de página (default 0)
    //   size   → registros por página (default 10)
    //   sortBy → campo de ordenamiento (default "id")
    //   dir    → "asc" | "desc" (default "asc")
    //
    // Ejemplo: GET /cursos/paginado?page=0&size=5&sortBy=nombre&dir=asc
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/paginado")
    public ResponseEntity<Page<CursoDTO>> findAllPaged(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int size,
            @RequestParam(defaultValue = "id")   String sortBy,
            @RequestParam(defaultValue = "asc")  String dir) {

        return ResponseEntity.ok(cursoService.findAllPaged(page, size, sortBy, dir));
    }

    // ─────────────────────────────────────────────────────────────────
    // GET BY NOMBRE — búsqueda parcial paginada
    // Ejemplo: GET /cursos/buscar?nombre=java&page=0&size=5
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/buscar")
    public ResponseEntity<Page<CursoDTO>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(cursoService.findByNombre(nombre, page, size));
    }

    // ─────────────────────────────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
        return cursoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─────────────────────────────────────────────────────────────────
    // POST — crear nuevo curso
    // ─────────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<CursoDTO> save(@RequestBody CursoDTO dto) {
        CursoDTO creado = cursoService.save(dto);
        return ResponseEntity.status(201).body(creado);
    }

    // ─────────────────────────────────────────────────────────────────
    // PUT — actualización COMPLETA (todos los campos)
    // ─────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizarCurso(
            @PathVariable Long id,
            @RequestBody CursoDTO dto) {

        return cursoService.actualizarCurso(id, dto);
    }

    // ─────────────────────────────────────────────────────────────────
    // PATCH — actualización PARCIAL (solo los campos enviados)
    // ─────────────────────────────────────────────────────────────────
    @PatchMapping("/{id}")
    public ResponseEntity<CursoDTO> patchCurso(
            @PathVariable Long id,
            @RequestBody CursoDTO parcial) {

        return cursoService.patchCurso(id, parcial);
    }

    // ─────────────────────────────────────────────────────────────────
    // DELETE — eliminar por ID
    // ─────────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return cursoService.deleteById(id);
    }
}