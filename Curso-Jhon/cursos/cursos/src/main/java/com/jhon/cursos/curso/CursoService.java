package com.jhon.cursos.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    // ─────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────

    /**
     * Guarda un nuevo curso. Recibe un DTO y devuelve un DTO.
     */
    public CursoDTO save(CursoDTO dto) {
        Curso curso = dto.toEntity();
        Curso guardado = cursoRepository.save(curso);
        return CursoDTO.fromEntity(guardado);
    }

    // ─────────────────────────────────────────────────────────────────
    // READ
    // ─────────────────────────────────────────────────────────────────

    /**
     * Retorna todos los cursos sin paginación.
     */
    public List<CursoDTO> findAll() {
        return cursoRepository.findAll()
                .stream()
                .map(CursoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retorna cursos con PAGINACIÓN y ORDENAMIENTO.
     *
     * @param page    número de página (0-based)
     * @param size    registros por página
     * @param sortBy  campo por el que se ordena (ej: "nombre", "precio")
     * @param dir     dirección: "asc" o "desc"
     */
    public Page<CursoDTO> findAllPaged(int page, int size, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return cursoRepository.findAll(pageable).map(CursoDTO::fromEntity);
    }

    /**
     * Busca un curso por su ID.
     */
    public Optional<CursoDTO> findById(Long id) {
        return cursoRepository.findById(id).map(CursoDTO::fromEntity);
    }

    /**
     * Busca cursos por nombre (paginado).
     */
    public Page<CursoDTO> findByNombre(String nombre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cursoRepository.findByNombreContaining(nombre, pageable).map(CursoDTO::fromEntity);
    }

    // ─────────────────────────────────────────────────────────────────
    // UPDATE COMPLETO (PUT) — reemplaza todos los campos
    // ─────────────────────────────────────────────────────────────────

    public ResponseEntity<CursoDTO> actualizarCurso(Long id, CursoDTO dtoActualizado) {
        try {
            Optional<Curso> optional = cursoRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Curso existente = optional.get();

            // PUT reemplaza TODOS los campos (incluso con null)
            existente.setNombre(dtoActualizado.getNombre());
            existente.setDescripcion(dtoActualizado.getDescripcion());
            existente.setCategoria(dtoActualizado.getCategoria());
            existente.setDuracionHoras(dtoActualizado.getDuracionHoras());
            existente.setPrecio(dtoActualizado.getPrecio());
            existente.setActivo(dtoActualizado.getActivo());
            existente.setFechaInicio(dtoActualizado.getFechaInicio());
            existente.setFechaFin(dtoActualizado.getFechaFin());
            existente.setNivel(dtoActualizado.getNivel());
            existente.setCapacidadMaxima(dtoActualizado.getCapacidadMaxima());

            Curso guardado = cursoRepository.save(existente);
            return ResponseEntity.ok(CursoDTO.fromEntity(guardado));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // UPDATE PARCIAL (PATCH) — solo actualiza campos no nulos
    // ─────────────────────────────────────────────────────────────────

    public ResponseEntity<CursoDTO> patchCurso(Long id, CursoDTO parcial) {
        try {
            Optional<Curso> optional = cursoRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Curso existente = optional.get();

            // PATCH actualiza solo los campos que vienen en el body (no nulos)
            if (parcial.getNombre() != null)          existente.setNombre(parcial.getNombre());
            if (parcial.getDescripcion() != null)     existente.setDescripcion(parcial.getDescripcion());
            if (parcial.getCategoria() != null)       existente.setCategoria(parcial.getCategoria());
            if (parcial.getDuracionHoras() != null)   existente.setDuracionHoras(parcial.getDuracionHoras());
            if (parcial.getPrecio() != null)          existente.setPrecio(parcial.getPrecio());
            if (parcial.getActivo() != null)          existente.setActivo(parcial.getActivo());
            if (parcial.getFechaInicio() != null)     existente.setFechaInicio(parcial.getFechaInicio());
            if (parcial.getFechaFin() != null)        existente.setFechaFin(parcial.getFechaFin());
            if (parcial.getNivel() != null)           existente.setNivel(parcial.getNivel());
            if (parcial.getCapacidadMaxima() != null) existente.setCapacidadMaxima(parcial.getCapacidadMaxima());

            Curso guardado = cursoRepository.save(existente);
            return ResponseEntity.ok(CursoDTO.fromEntity(guardado));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────

    public ResponseEntity<Void> deleteById(Long id) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}