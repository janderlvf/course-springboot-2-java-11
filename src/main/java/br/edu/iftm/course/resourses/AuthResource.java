package br.edu.iftm.course.resourses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.course.dto.CredentialsDTO;
import br.edu.iftm.course.dto.TokenDTO;
import br.edu.iftm.course.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO dto){		
	
		
		TokenDTO tokenDTO =  service.authenticate(dto);
		return ResponseEntity.ok().body(tokenDTO);		
	}

}
