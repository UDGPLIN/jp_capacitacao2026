package br.com.indra.danilo_lins.service;

import br.com.indra.danilo_lins.exception.BusinessException;
import br.com.indra.danilo_lins.model.HistoricoPreco;
import br.com.indra.danilo_lins.model.Produtos;
import br.com.indra.danilo_lins.repository.HistoricoPrecoRepository;
import br.com.indra.danilo_lins.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutosService {

    private static final Logger log = LogManager.getLogger(ProdutosService.class);
    private final ProdutosRepository produtosRepository;
    private final HistoricoPrecoRepository historicoPrecoRepository;

    public List<Produtos> getAll() {
        return produtosRepository.findAll();
    }

    public Produtos createdProduto(Produtos produto) {

        if(produto.getNome() == null || produto.getNome().isEmpty()){
            throw new BusinessException("Nome do produto é obrigatório");
        }

        if(produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("Preço deve ser maior que zero");
        }

        log.info("Produto Criado com sucesso");
        return produtosRepository.save(produto);
    }

    public Produtos atualiza(Long id, Produtos produto) {

        if(produto.getNome() == null || produto.getNome().isEmpty()){
            throw new BusinessException("Nome do produto é obrigatório");
        }

        if(produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("Preço deve ser maior que zero");
        }

        Produtos existente = produtosRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Produto não encontrado"));

        existente.setNome(produto.getNome());
        existente.setPreco(produto.getPreco());
        existente.setDescricao(produto.getDescricao());
        existente.setCodigoBarras(produto.getCodigoBarras());
        existente.setCategoria(produto.getCategoria());

        log.info("Produto atualizado com sucesso");
        return produtosRepository.save(existente);
        //return produtosRepository.save(produto);
    }

    public void deletarProduto(Long id) {

        produtosRepository.deleteById(id);
    }

    public Produtos getById(Long id) {

        return produtosRepository.findById(id).get();
    }

    public Produtos atualizaPreco(Long id, BigDecimal preco) {

            Produtos produto = produtosRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            BigDecimal precoAntigo = produto.getPreco();

            produto.setPreco(preco);
            produtosRepository.save(produto);

            HistoricoPreco historico = new HistoricoPreco();
            historico.setProdutos(produto);
            historico.setPrecoAntigo(precoAntigo);
            historico.setPrecoNovo(preco);
            historico.setDataAlteracao(LocalDateTime.now());

            historicoPrecoRepository.save(historico);

            log.info("Preço do produto atualizado com sucesso");
            return produto;

    }

    public List <Produtos> buscarPorNome(String nome){

            return produtosRepository.findByNomeContainingIgnoreCase(nome);
        }

    public List <Produtos> buscarPorCategoria(Long categoriaId) {

            return produtosRepository.findByCategoriaId(categoriaId);
        }


}
