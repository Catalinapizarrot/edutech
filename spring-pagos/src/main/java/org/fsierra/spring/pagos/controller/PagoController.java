package org.fsierra.spring.pagos.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fsierra.spring.pagos.models.entity.Pago;
import org.fsierra.spring.pagos.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pagos")
@Tag(name = "Pagos", description = "CRUD de pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    public List<Pago> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id)
                .map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody Pago pago,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }
        // En POST también se incluye estadoPago, tal como viene en el JSON
        Pago creado = service.guardar(pago);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @Valid @RequestBody Pago pago,
            BindingResult result,
            @PathVariable Long id
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return service.porId(id).map(p -> {
            p.setIdUsuario(pago.getIdUsuario());
            p.setMonto(pago.getMonto());
            p.setFechaPago(pago.getFechaPago());
            p.setEstadoPago(pago.getEstadoPago()); // Aseguramos setear el estado aquí también
            Pago actualizado = service.guardar(p);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(actualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.porId(id).map(p -> {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(),
                        "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
