package br.edu.iftm.course.resourses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.course.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResourse {
	
	@GetMapping
	public ResponseEntity<User>findAll(){
		
		User u = new User(1L,"MARIA", "MARIA@GMAIL.COM","8888", "1234");
		return ResponseEntity.ok().body(u);
	}

}
