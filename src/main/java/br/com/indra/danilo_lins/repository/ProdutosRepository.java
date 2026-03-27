package br.com.indra.danilo_lins.repository;

import br.com.indra.danilo_lins.model.Produtos;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long>{

    List<Produtos> findByNomeContainingIgnoreCase(String nome);
    List<Produtos> findByCategoriaId(Long categoriaId);

}
