package org.fsierra.spring.cursos.controllers;

import jakarta.validation.Valid;
import org.fsierra.spring.cursos.models.entity.Curso;
import org.fsierra.spring.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id)
                .map(curso -> ResponseEntity.ok().body(curso))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }

        return service.porId(id).map(c -> {
            c.setTitulo(curso.getTitulo());
            c.setDescripcion(curso.getDescripcion());
            c.setInstructor(curso.getInstructor());
            c.setEstado(curso.getEstado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(c));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.porId(id).map(curso -> {
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
