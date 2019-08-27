package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	

}
