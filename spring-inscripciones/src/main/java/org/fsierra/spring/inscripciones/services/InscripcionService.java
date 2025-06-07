package org.fsierra.spring.inscripciones.services;

import org.fsierra.spring.inscripciones.models.entity.Inscripcion;
import java.util.List;
import java.util.Optional;

public interface InscripcionService {
    List<Inscripcion> listar();
    Optional<Inscripcion> porId(Long id);
    Inscripcion guardar(Inscripcion inscripcion);
    void eliminar(Long id);
}
