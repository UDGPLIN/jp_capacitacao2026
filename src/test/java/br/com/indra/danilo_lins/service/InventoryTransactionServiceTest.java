package br.com.indra.danilo_lins.service;

import br.com.indra.danilo_lins.exception.BusinessException;
import br.com.indra.danilo_lins.model.Produtos;
import br.com.indra.danilo_lins.repository.InventoryTransactionRepository;
import br.com.indra.danilo_lins.repository.ProdutosRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryTransactionServiceTest {

    @Mock
    private ProdutosRepository produtosRepository;

    @Mock
    private InventoryTransactionRepository inventoryRepository;

    @InjectMocks
    private InventoryTransactionService service;

    @Test
    void deveAdicionarEstoqueComSucesso() throws IOException {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setEstoque(10);

        when(produtosRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        service.addProduct(1L, 5);

        assertEquals(15, produto.getEstoque());
        verify(inventoryRepository, times(1)).save(any());
    }

    @Test
    void deveFalharSeProdutoNaoExistir() {

        when(produtosRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.addProduct(1L, 5);
        });
    }

    @Test
    void deveLancarErroQuandoEstoqueInsuficiente() {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setEstoque(5);

        when(produtosRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        assertThrows(BusinessException.class, () -> {
            service.removeProduct(1L, 10);
        });

    }

}

