package org.fsierra.spring.reportes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fsierra.spring.reportes.models.entity.Reporte;
import org.fsierra.spring.reportes.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "CRUD de reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    public List<Reporte> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id)
                .map(r -> ResponseEntity.ok(r))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody Reporte reporte,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Reporte creado = service.guardar(reporte);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @Valid @RequestBody Reporte reporte,
            BindingResult result,
            @PathVariable Long id
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return service.porId(id).map(r -> {
            r.setIdUsuario(reporte.getIdUsuario());
            r.setTipoReporte(reporte.getTipoReporte());
            r.setFechaPago(reporte.getFechaPago());
            r.setFechaGeneracion(reporte.getFechaGeneracion());
            Reporte actualizado = service.guardar(r);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(actualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.porId(id).map(r -> {
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
