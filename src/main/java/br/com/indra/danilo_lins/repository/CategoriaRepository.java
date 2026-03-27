package br.com.indra.danilo_lins.repository;

import br.com.indra.danilo_lins.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    boolean existsByNomeIgnoreCase(String nome);

}
