package br.com.indra.danilo_lins.repository;

import br.com.indra.danilo_lins.model.HistoricoPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, UUID> {
    /**
     * Ele cria uma query fazendo busca por pdroduto id
     * Select * from historico_preco where produtos_id = :produtoId;
     * @param produtoId
     * @return HistoricoPreco
     */
    Set<HistoricoPreco> findByProdutosId(Long produtoId);

}