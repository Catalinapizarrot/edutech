package org.fsierra.spring.inscripciones.controller;

import jakarta.validation.Valid;
import org.fsierra.spring.inscripciones.models.entity.Inscripcion;
import org.fsierra.spring.inscripciones.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @GetMapping
    public List<Inscripcion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id)
                .map(inscripcion -> ResponseEntity.ok().body(inscripcion))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Inscripcion inscripcion, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(inscripcion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Inscripcion inscripcion, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }

        return service.porId(id).map(i -> {
            i.setIdUsuario(inscripcion.getIdUsuario());
            i.setIdCurso(inscripcion.getIdCurso());
            i.setEstado(inscripcion.getEstado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(i));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.porId(id).map(inscripcion -> {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}