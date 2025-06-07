package org.fsierra.spring.usuarios.services;

import org.fsierra.spring.usuarios.models.entity.Usuario;
import org.fsierra.spring.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        repository.findAll().forEach(usuarios::add);
        return usuarios;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Si no se ha asignado fechaRegistro, la asigna automáticamente
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDate.now());
        }

        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional <Usuario> buscarPorEmail (String email){
        return repository.findByEmail(email);
    }
}
