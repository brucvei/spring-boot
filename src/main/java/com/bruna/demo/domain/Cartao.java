package com.bruna.demo.domain;

import com.bruna.demo.domain.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;

@Entity
@JsonTypeName("cartao")
public class Cartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public Cartao() {}

    public Cartao(Integer id, StatusPagamento status, Pedido pedido, Integer numeroDeParcelas) {
        super(id, status, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
