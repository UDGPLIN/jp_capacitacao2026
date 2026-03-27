package br.com.indra.danilo_lins.service;

import br.com.indra.danilo_lins.exception.BusinessException;
import br.com.indra.danilo_lins.exception.ResourceNotFoundException;
import br.com.indra.danilo_lins.model.InventoryTransaction;
import br.com.indra.danilo_lins.model.Produtos;
import br.com.indra.danilo_lins.repository.InventoryTransactionRepository;
import br.com.indra.danilo_lins.repository.ProdutosRepository;
import br.com.indra.danilo_lins.service.dto.InventoryResponseDTO;
import br.com.indra.danilo_lins.service.dto.InventoryTransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProdutosRepository produtosRepository;

    public void addProduct(Long productId, Integer quantity) {

        Produtos produto = produtosRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produto.getEstoque() == null) {
            produto.setEstoque(0);
        }

        produto.setEstoque(produto.getEstoque() + quantity);
        produtosRepository.save(produto);

        InventoryTransaction tx = new InventoryTransaction();
        tx.setProductId(productId);
        tx.setDelta(quantity);
        tx.setReason("ENTRADA");
        tx.setCreateAt(LocalDateTime.now());

        inventoryTransactionRepository.save(tx);
        log.info("Transação de Entrada Produto");
    }

    public void removeProduct(Long productId, Integer quantity) {

        Produtos produto = produtosRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produto.getEstoque() == null || produto.getEstoque() < quantity) {
            throw new BusinessException("Estoque insuficiente");
        }

        produto.setEstoque(produto.getEstoque() - quantity);
        produtosRepository.save(produto);

        InventoryTransaction tx = new InventoryTransaction();
        tx.setProductId(productId);
        tx.setDelta(-quantity);
        tx.setReason("SAIDA");
        tx.setCreateAt(LocalDateTime.now());

        inventoryTransactionRepository.save(tx);
        log.info("Transação de Saida Produto");
    }

    public List<InventoryTransaction> getAllProducts() {
        return inventoryTransactionRepository.findAll();
    }

    public List<InventoryTransaction> getByProductId (Long productId) {

        if(!produtosRepository.existsById(productId)) {

            throw new ResourceNotFoundException("Produto não encontrado");
        }

        log.info("Produto encontrado com sucesso");
        return inventoryTransactionRepository.findByProductId(productId);

    }

    public InventoryResponseDTO getInventoryByProduct(Long productId) {

        Produtos produto = produtosRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        List<InventoryTransaction> transactions = inventoryTransactionRepository.findByProductId(productId);

        List<InventoryTransactionDTO> transactionsDTO = transactions.stream()
                .map(tx ->InventoryTransactionDTO.builder()
                        .delta(tx.getDelta())
                        .reason(tx.getReason())
                        .data(tx.getCreateAt())
                        .build())
                .toList();

        return InventoryResponseDTO.builder()
                .productid(produto.getId())
                .produto(produto.getNome())
                .estoqueAtual(produto.getEstoque())
                .transactions(transactionsDTO)
                .build();
    }
}