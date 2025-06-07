package org.fsierra.spring.usuarios.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fsierra.spring.usuarios.models.entity.Usuario;
import org.fsierra.spring.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "CRUD de usuarios con contraseña encriptada")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // 🔹

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id)
                .map(u -> ResponseEntity.ok(u))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody Usuario usuario,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.buscarPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"));
        }

        // 🔹 encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario creado = service.guardar(usuario);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @Valid @RequestBody Usuario usuario,
            BindingResult result,
            @PathVariable Long id
    ) {
        if (result.hasErrors()) {
            return validar(result);
        }

        return service.porId(id).map(u -> {
            u.setNombre(usuario.getNombre());
            u.setEmail(usuario.getEmail());
            // 🔹 solo re-encriptamos si nos envían una contraseña nueva
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                u.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            Usuario actualizado = service.guardar(u);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(actualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return service.porId(id).map(u -> {
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
