package br.edu.iftm.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Order;
import br.edu.iftm.course.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByClient(User client);

}
