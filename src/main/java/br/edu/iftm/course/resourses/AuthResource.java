package br.edu.iftm.course.resourses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.course.dto.CredentialsDTO;
import br.edu.iftm.course.dto.EmailDTO;
import br.edu.iftm.course.dto.TokenDTO;
import br.edu.iftm.course.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private AuthService service;

	@PostMapping(value = "/login")
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO dto) {

		TokenDTO tokenDTO = service.authenticate(dto);
		return ResponseEntity.ok().body(tokenDTO);
	}

	@PostMapping(value = "/refresh")
	public ResponseEntity<TokenDTO> refresh() {

		TokenDTO tokenDTO = service.refreschToken();
		return ResponseEntity.ok().body(tokenDTO);
	}

	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@RequestBody EmailDTO dto) {

		service.sendNewPassword(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	// minuto 15:27

}