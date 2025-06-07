package org.fsierra.spring.pagos.services;

import org.fsierra.spring.pagos.models.entity.Pago;
import org.fsierra.spring.pagos.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> listar() {
        List<Pago> pagos = new ArrayList<>();
        repository.findAll().forEach(pagos::add);
        return pagos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> buscarPorIdUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional
    public Pago guardar(Pago pago) {
        return repository.save(pago);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
