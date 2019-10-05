package br.edu.iftm.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.course.dto.OrderDTO;
import br.edu.iftm.course.entities.Order;
import br.edu.iftm.course.entities.User;
import br.edu.iftm.course.repositories.OrderRepository;
import br.edu.iftm.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private AuthService authService;
	
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
		
	}
	
	public OrderDTO findById(Long id) {
		
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		authService.validateOwnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}
	
	public List<OrderDTO> findByClient() {
		
		User client = authService.authenticated();
		List<Order> list = repository.findByClient(client);
		return list.stream().map (e -> new OrderDTO(e)).collect(Collectors.toList());
		
		
	}
	
	

}
