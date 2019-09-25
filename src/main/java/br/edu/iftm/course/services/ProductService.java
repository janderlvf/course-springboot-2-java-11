	package br.edu.iftm.course.services;
	

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iftm.course.dto.CategoryDTO;
import br.edu.iftm.course.dto.ProductCategoriesDTO;
import br.edu.iftm.course.dto.ProductDTO;
import br.edu.iftm.course.entities.Category;
import br.edu.iftm.course.entities.Product;
import br.edu.iftm.course.repositories.CategoryRepository;
import br.edu.iftm.course.repositories.ProductRepository;
import br.edu.iftm.course.services.exceptions.DatabaseException;
import br.edu.iftm.course.services.exceptions.ResourceNotFoundException;
	
	@Service
	public class ProductService {
		
		@Autowired
		private ProductRepository repository;
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public Page<ProductDTO> findAllPaged(Pageable pageable) {
			Page<Product> list = repository.findAll(pageable);
			return list.map(e -> new ProductDTO(e));
			}
		
		
	public ProductDTO findById(Long id) {
			
			Optional<Product> obj = repository.findById(id);
			Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
			return new ProductDTO(entity);
		}
	
	@Transactional
	public ProductDTO insert(ProductCategoriesDTO dto) {
		Product entity = dto.toEntity();
		SetProductCategories(entity, dto.getCategories());
		
		 entity = repository.save(entity);
		 return new ProductDTO(entity);
	}
	
	
	
	
	@Transactional
	public ProductDTO update(Long id, ProductCategoriesDTO dto) {		
		try {
			Product entity = repository.getOne(id);
			updateData(entity,dto);
			entity =  repository.save(entity);
			return new ProductDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	
	public void delete(Long id) {		
		try {		
		repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {			
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {	
			throw new DatabaseException(e.getMessage());
		}
}

	private void updateData(Product entity, ProductCategoriesDTO dto) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		if(dto.getCategories() != null && dto.getCategories().size() > 0) {
			SetProductCategories(entity, dto.getCategories());
		}
	
	}
	private void SetProductCategories(Product entity, List<CategoryDTO> categories) {		
		entity.getCategories().clear();
		for(CategoryDTO dto : categories) {			
			Category category = categoryRepository.getOne(dto.getId());
			entity.getCategories().add(category);			
		}		
	}
	
	
	
	}
