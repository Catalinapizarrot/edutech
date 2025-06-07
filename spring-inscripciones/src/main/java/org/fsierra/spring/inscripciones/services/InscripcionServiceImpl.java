package org.fsierra.spring.inscripciones.services;

import org.fsierra.spring.inscripciones.models.entity.Inscripcion;
import org.fsierra.spring.inscripciones.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private InscripcionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Inscripcion> listar() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        repository.findAll().forEach(inscripciones::add);
        return inscripciones;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inscripcion> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Inscripcion guardar(Inscripcion inscripcion) {
        if (inscripcion.getFechaInscripcion() == null) {
            inscripcion.setFechaInscripcion(LocalDate.now());
        }
        return repository.save(inscripcion);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
