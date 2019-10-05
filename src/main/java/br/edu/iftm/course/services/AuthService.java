package br.edu.iftm.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iftm.course.dto.CredentialsDTO;
import br.edu.iftm.course.dto.TokenDTO;
import br.edu.iftm.course.entities.Order;
import br.edu.iftm.course.entities.User;
import br.edu.iftm.course.repositories.UserRepository;
import br.edu.iftm.course.security.JWTUtil;
import br.edu.iftm.course.services.exceptions.JWTAuthenticationException;
import br.edu.iftm.course.services.exceptions.JWTAuthorizationException;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserRepository UserRepository;

	@Transactional(readOnly = true)
	public TokenDTO authenticate(CredentialsDTO dto) {
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),
					dto.getPassword());

			authenticationManager.authenticate(authToken);

			String token = jwtUtil.generateToken(dto.getEmail());
			return new TokenDTO(dto.getEmail(), token);
		} catch (AuthenticationException e) {

			throw new JWTAuthenticationException("Bad credentials");

		}
	}

	public User authenticated() {

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			return UserRepository.findByEmail(userDetails.getUsername());
		} catch (Exception e) {

			throw new JWTAuthorizationException("Acess denied");
		}
	}

	public void validateSelfOrAdmin(Long userId) {

		User user = authenticated();
		if (user == null || (!user.getId().equals(userId)) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Acess denied");

		}

	}
	
	public void validateOwnOrderOrAdmin(Order order) {

		User user = authenticated();
		if (user == null || (!user.getId().equals(order.getClient().getId())) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Acess denied");

		}

	}
	
	public TokenDTO refreschToken() {
		
		User user = authenticated();
		return new TokenDTO(user.getEmail(), jwtUtil.generateToken(user.getEmail()));
		
	}

}
