package com.bruna.demo.repositories;

import com.bruna.demo.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
