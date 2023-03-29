package com.bruna.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruna.demo.domain.Categoria;
import com.bruna.demo.repositories.CategoriaRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2));
	}

}
