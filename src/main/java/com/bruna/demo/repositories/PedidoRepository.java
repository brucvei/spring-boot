package com.bruna.demo.repositories;

import com.bruna.demo.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository extends JpaRepository<Pedido, Integer> {
}
