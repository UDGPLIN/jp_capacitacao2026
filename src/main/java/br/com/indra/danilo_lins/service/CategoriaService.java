package br.com.indra.danilo_lins.service;

import br.com.indra.danilo_lins.exception.BusinessException;
import br.com.indra.danilo_lins.exception.ResourceNotFoundException;
import br.com.indra.danilo_lins.model.Categoria;
import br.com.indra.danilo_lins.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private static final Logger logger = Logger.getLogger(String.valueOf(CategoriaService.class));
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> getAll(){
        return categoriaRepository.findAll();
    }

    public Categoria createCategoria(Categoria categoria){

        if(categoria.getNome() == null || categoria.getNome().isBlank()){
            throw new BusinessException("Nome da categoria é obrigatório");
        }

        if(categoriaRepository.existsByNomeIgnoreCase((categoria.getNome()))){
            throw new BusinessException("Categoria já existente");
        }

        log.info("Categoria criada com sucesso");
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria categoria){
        Categoria existingCategoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));

        if(categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())
                && !existingCategoria.getNome().equalsIgnoreCase(categoria.getNome())){

            throw new BusinessException("Categoria já existe");
        }

        existingCategoria.setNome(categoria.getNome());
        existingCategoria.setParent(categoria.getParent());
        log.info("Categoria atualizado com sucesso");
        return categoriaRepository.save(existingCategoria);
    }

    public void deleteCategoria(Long id){

        categoriaRepository.deleteById(id);
        log.info("Categoria deletada com sucesso");
    }

}
