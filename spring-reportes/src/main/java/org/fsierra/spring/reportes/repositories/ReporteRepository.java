package org.fsierra.spring.reportes.repositories;

import org.fsierra.spring.reportes.models.entity.Reporte;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReporteRepository extends CrudRepository<Reporte, Long> {
    List<Reporte> findByIdUsuario(Long idUsuario);
}
