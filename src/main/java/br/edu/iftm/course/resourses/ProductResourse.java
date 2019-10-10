package br.edu.iftm.course.resourses;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iftm.course.dto.ProductCategoriesDTO;
import br.edu.iftm.course.dto.ProductDTO;
import br.edu.iftm.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResourse {
	
	@Autowired
	private ProductService service;
	
	
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>>findAllPaged(
			@RequestParam (value = "page", defaultValue = "0") Integer page,
			@RequestParam (value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam (value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam (value = "direction", defaultValue = "ASC") String direction){
		
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ProductDTO> list = service.findAllPaged(pageRequest);

		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/category/{categoryId}")
	public ResponseEntity<Page<ProductDTO>>findByCategoryPaged(
			@PathVariable Long categoryId,
			@RequestParam (value = "page", defaultValue = "0") Integer page,
			@RequestParam (value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam (value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam (value = "direction", defaultValue = "ASC") String direction){
		
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ProductDTO> list = service.findByCategoryPaged(categoryId, pageRequest);

		return ResponseEntity.ok().body(list);
	}
		

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
		
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductCategoriesDTO dto){		
		ProductDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
		
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductCategoriesDTO dto){
		
		ProductDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
