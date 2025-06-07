package org.fsierra.spring.reportes.services;

import org.fsierra.spring.reportes.models.entity.Reporte;
import org.fsierra.spring.reportes.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> listar() {
        List<Reporte> reportes = new ArrayList<>();
        repository.findAll().forEach(reportes::add);
        return reportes;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reporte> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> buscarPorIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional
    public Reporte guardar(Reporte reporte) {
        return repository.save(reporte);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
