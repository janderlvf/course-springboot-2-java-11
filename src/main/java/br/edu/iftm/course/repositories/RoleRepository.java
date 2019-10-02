package br.edu.iftm.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.course.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
