package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	

}
