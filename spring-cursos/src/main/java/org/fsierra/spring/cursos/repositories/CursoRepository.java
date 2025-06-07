package org.fsierra.spring.cursos.repositories;

import org.fsierra.spring.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {


}
