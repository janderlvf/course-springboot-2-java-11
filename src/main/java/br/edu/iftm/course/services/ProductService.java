	package br.edu.iftm.course.services;
	
	import java.util.List;
	import java.util.Optional;
	import java.util.stream.Collectors;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iftm.course.dto.CategoryDTO;
	import br.edu.iftm.course.dto.ProductCategoriesDTO;
	import br.edu.iftm.course.dto.ProductDTO;
	import br.edu.iftm.course.entities.Category;
	import br.edu.iftm.course.entities.Product;
	import br.edu.iftm.course.repositories.CategoryRepository;
	import br.edu.iftm.course.repositories.ProductRepository;
	import br.edu.iftm.course.services.exceptions.ResourceNotFoundException;
	
	@Service
	public class ProductService {
		
		@Autowired
		private ProductRepository repository;
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		public List<ProductDTO> findAll(){
			List<Product> list = repository.findAll();
			return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
			
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
	
	
	private void SetProductCategories(Product entity, List<CategoryDTO> categories) {
	
		entity.getCategories().clear();
		for(CategoryDTO dto : categories) {
			
			Category category = categoryRepository.getOne(dto.getId());
			entity.getCategories().add(category);
			
		}		
	}
	
	}
