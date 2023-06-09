package com.bruna.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruna.demo.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}