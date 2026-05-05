package com.jhon.estudiantes.estudiante;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    // GET ALL
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    // GET BY ID
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    // POST
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
}