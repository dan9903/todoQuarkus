package br.com.stefanini.maratonadev.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.TodoStatus;
import io.quarkus.panache.common.Sort;

@RequestScoped
public class TodoStatusDAO {

	@Transactional
	public void insert(TodoStatus status) {
		TodoStatus.persist(status);
	}

	public List<TodoStatus> findStatusByTodo(Long idTodo) {

		return TodoStatus.list("todo", Sort.by("data").descending(), new Todo(idTodo));
	}
}
