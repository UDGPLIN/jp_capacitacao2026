package br.com.indra.danilo_lins.controller;

import br.com.indra.danilo_lins.model.Categoria;
import br.com.indra.danilo_lins.repository.CategoriaRepository;
import br.com.indra.danilo_lins.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.indra.danilo_lins.exception.BusinessException;
import java.util.List;

@RequestMapping ("/categorias")
@RequiredArgsConstructor
@RestController
public class CategoriaController {

    private static final Logger log = LogManager.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;

    @PostMapping("/cria")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria){

        log.info("Iniciando criarCategoria");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.createCategoria(categoria));
    }

    /**
     * localhost:9090/categorias
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){

        log.info("Iniciando getAll Categoria");
        return ResponseEntity.ok(categoriaService.getAll());

    }

    @PutMapping("/atualiza/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id,
                                                     @RequestBody Categoria categoria){
        log.info("Iniciando atualizarCategoria");
        return ResponseEntity.ok(categoriaService.updateCategoria(id, categoria));
    }

    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<Categoria> deletarCategoria(@PathVariable Long id){
        log.info("Iniciando deletarCategoria");
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
