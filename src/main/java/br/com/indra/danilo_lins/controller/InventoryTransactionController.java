package br.com.indra.danilo_lins.controller;

import br.com.indra.danilo_lins.model.InventoryTransaction;
import br.com.indra.danilo_lins.service.InventoryTransactionService;
import br.com.indra.danilo_lins.service.dto.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService service;

    @PostMapping("/{productId}/add")
    public ResponseEntity<Void> addProduct(@PathVariable Long productId,
                                    @RequestParam Integer quantity) {
        log.info("Iniciando addProduct");
        service.addProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/remove")
    public ResponseEntity<Void> removeProduct(@PathVariable Long productId,
                                       @RequestParam Integer quantity) {
        log.info("Iniciando removeProduct");
        service.removeProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

    /**
     * localhost:9090/inventory
     * @return
     */
    @GetMapping
    public ResponseEntity<List<InventoryTransaction>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    //@GetMapping("/{productId}")
    //public ResponseEntity<List<InventoryTransaction>> getByProduct(@PathVariable Long productId) {

    //    return ResponseEntity.ok(service.getByProductId(productId));
    //}

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getByProduct(@PathVariable Long productId) {

        return ResponseEntity.ok(service.getInventoryByProduct(productId));

    }

}
