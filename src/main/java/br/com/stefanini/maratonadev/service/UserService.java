package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.UserDAO;
import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.parser.UserParser;

@RequestScoped
public class UserService {
	@Inject
	UserDAO dao;

	public void validate(User user) {
		if (user.equals(findUserByEmail(user.getEmail())))
			throw new NotFoundException();
	}

	public User findUserByEmail(String email) {
		return dao.findByEmail(email);
	}

	public void insert(@Valid UserDTO userDTO) {
		User user = UserParser.get().entity(userDTO);
		validate(user);
		dao.insert(user);
	}

	public List<UserDTO> getAll() {
		return dao.getAll().stream().map(UserParser.get()::dto).collect(Collectors.toList());
	}
}
