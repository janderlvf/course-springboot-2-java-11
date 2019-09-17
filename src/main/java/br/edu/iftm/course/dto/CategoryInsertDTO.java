package br.edu.iftm.course.dto;

import java.io.Serializable;

import br.edu.iftm.course.entities.Category;
import br.edu.iftm.course.entities.User;

public class CategoryInsertDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	
	public CategoryInsertDTO() {
		
	}

	public CategoryInsertDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CategoryInsertDTO(User entity) {
		
		this.id = entity.getId();
		this.name = entity.getName();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Category toEntity() {		
		return new Category(id, name);
	}
}
