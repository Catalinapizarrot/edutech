package org.fsierra.spring.pagos.services;

import org.fsierra.spring.pagos.models.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> listar();
    Optional<Pago> porId(Long id);
    Pago guardar(Pago pago);
    void eliminar(Long id);
    List<Pago> buscarPorIdUsuario(Long idUsuario);
}
