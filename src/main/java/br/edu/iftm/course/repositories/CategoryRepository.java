package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	

}
