package br.com.stefanini.maratonadev.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.model.Todo;

@RequestScoped
@Traced
public class TodoDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	/**
	 * Insert Todo e return o created ID
	 * 
	 * @param todo
	 * @return
	 */
	public Long insert(Todo todo) {
		todo.persistAndFlush();
		return todo.getId();
	}

	@Transactional
	/**
	 * 
	 * @param todo
	 */
	public void update(Todo todo) {
		todo.persistAndFlush();
	}

	/**
	 * 
	 * @return List<Todo>
	 */

	public List<Todo> getAll() {
		String sql = "GET_TODOS";
		List<Todo> todos;
		TypedQuery<Todo> query = em.createNamedQuery(sql, Todo.class);

		try {
			todos = query.getResultList();
		} catch (NoResultException e) {
			todos = new ArrayList<Todo>();
		}

		return todos;
	}

	public Boolean repeatedName(String name) {
		String sql = "HAS_NAME_TASK";
		Boolean isRepeated = Boolean.FALSE;

		TypedQuery<Todo> query = em.createNamedQuery(sql, Todo.class);

		query.setParameter("nome", "%" + name + "%");

		isRepeated = query.getResultList().size() > 0;

		return isRepeated;
	}

	public Todo findById(Long id) {
		String sql = "FIND_TODO_BY_ID";
		Todo todo;
		TypedQuery<Todo> query = em.createNamedQuery(sql, Todo.class);
		query.setParameter("id", id);
		try {
			todo = query.getSingleResult();
		} catch (NoResultException e) {
			todo = null;
		}
		return todo;
	}

	@Transactional
	public void delete(Long id) {
		Todo.deleteById(id);
	}

}
