package br.com.stefanini.maratonadev.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.model.User;

@RequestScoped
public class UserDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	/**
	 * 
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		return User.find("email", email).firstResult();
	}

	@Transactional
	/**
	 * 
	 * @param user
	 * @return
	 */
	public UUID insert(User user) {
		user.persistAndFlush();
		return user.getId();
	}

	public List<User> getAll() {
		List<User> users;

		try {
			users = User.listAll();
		} catch (NoResultException e) {
			users = new ArrayList<User>();
		}

		return users;
	}
}
