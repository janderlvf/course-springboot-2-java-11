package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	

}
