package br.com.indra.danilo_lins.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class InventoryResponseDTO {

    private Long productid;
    private String produto;
    private Integer estoqueAtual;
    private List<InventoryTransactionDTO> transactions;

}
