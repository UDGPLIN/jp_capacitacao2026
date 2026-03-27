package br.com.indra.danilo_lins.controller;

import br.com.indra.danilo_lins.model.Produtos;
import br.com.indra.danilo_lins.service.ProdutosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;

    //C

    /**
     * Recomendação de desenvolvimento, ampliar responses(responseEntity)
     * possíveis além do ok.
     */
    @Operation(description = "Endpoint para criar um novo produto",
            summary = "Criação de produto")
    @PostMapping("/cria")
    public ResponseEntity<Produtos> criarProduto(@RequestBody Produtos produto){

        log.info("Criando produto {}", produto);
        return ResponseEntity.ok(produtosService.createdProduto(produto));
    }

    /**
     * GET
     * localhost:9090/produtos
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Produtos>> getAll(){

        log.info("Listando todos os produtos");
        return ResponseEntity.ok(produtosService.getAll());
    }

    /**
     * GET
     * localhost:9090/produtos/1
     * @return
     */
   // @GetMapping("/{id}")
   // public ResponseEntity<Produtos> getById(@PathVariable Long id){
   //     return ResponseEntity.ok(produtosService.getById(id));
   // }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id){
        Optional<Produtos> produto0 = Optional.ofNullable(produtosService.getById(id));
        if (produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produto0.get());
    }

    //U
    @PutMapping("/atualiza")
    public ResponseEntity<Produtos> atualizarProduto(@RequestParam Long id,
                                                     @RequestBody Produtos produto){
        log.info("Atualizando produto {}", produto);
        return ResponseEntity.ok(produtosService.atualiza(id, produto));
    }

    //@PatchMapping("/atualiza-preco/{id}")
    //public ResponseEntity<Produtos> atualizarProdutoParcial(@PathVariable Long id,
    //                                                       @RequestParam BigDecimal preco) {
    //    return ResponseEntity.ok(produtosService.atualizaPreco(id, preco));
    //}

    @PatchMapping("/atualiza/{id}")
    public ResponseEntity<Object> atualizarProdutoParcial (@PathVariable Long id,
                                                             @RequestParam BigDecimal preco){
        Optional<Produtos> produto1 = Optional.ofNullable(produtosService.getById(id));
        if (produto1.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        return ResponseEntity.ok(produtosService.atualizaPreco(id, preco));
    }

    //Mudar para delete lógico
    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtosService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<Produtos>> buscarPorNome(@RequestParam String nome){
        return ResponseEntity.ok(produtosService.buscarPorNome(nome));

    }
}
