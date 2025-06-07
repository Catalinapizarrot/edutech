package org.fsierra.spring.reportes.services;

import org.fsierra.spring.reportes.models.entity.Reporte;

import java.util.List;
import java.util.Optional;

public interface ReporteService {
    List<Reporte> listar();
    Optional<Reporte> porId(Long id);
    Reporte guardar(Reporte reporte);
    void eliminar(Long id);
    List<Reporte> buscarPorIdUsuario(Long idUsuario);
}
