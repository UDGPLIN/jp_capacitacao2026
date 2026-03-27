package br.com.indra.danilo_lins.repository;

import br.com.indra.danilo_lins.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository <InventoryTransaction, Long> {

    List<InventoryTransaction> findByProductId(Long productId);
}
