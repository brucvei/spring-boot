package com.bruna.demo.services;

import com.bruna.demo.domain.Boleto;
import com.bruna.demo.domain.ItemPedido;
import com.bruna.demo.domain.Pedido;
import com.bruna.demo.domain.enums.StatusPagamento;
import com.bruna.demo.repositories.ItemPedidoRepository;
import com.bruna.demo.repositories.PagamentoRepository;
import com.bruna.demo.repositories.PedidoRepository;
import com.bruna.demo.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pgtoRepo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepo;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(
                (() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()
                )
                )
        );
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setStatus(StatusPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof Boleto) {
            Boleto pagto = (Boleto) obj.getPagamento();
            boletoService.preencherPagamento(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pgtoRepo.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepo.saveAll(obj.getItens());
        return obj;
    }
}
