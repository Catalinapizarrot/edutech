package org.fsierra.spring.pagos.repositories;

import org.fsierra.spring.pagos.models.entity.Pago;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PagoRepository extends CrudRepository<Pago, Long> {
    List<Pago> findByIdUsuario(Long idUsuario);
}
