package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	

}
