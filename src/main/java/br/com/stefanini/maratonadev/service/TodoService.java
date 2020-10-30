package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.dao.TodoDAO;
import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.dominio.StatusEnum;
import br.com.stefanini.maratonadev.model.parser.TodoParser;

@RequestScoped
@Traced
public class TodoService {

	@Inject
	TodoDAO dao;

	@Inject
	TodoStatusService statusService;

	@Inject
	UserService userService;

	private void validate(Todo todo) {
		// validate business rules

		if (dao.repeatedName(todo.getName())) {
			throw new NotFoundException();
		}
	}

	@Transactional(rollbackOn = Exception.class)
	/**
	 * Insertion has data from system time
	 */
	public void insert(@Valid TodoDTO todoDto, String loggedMail) {
		Todo todo = TodoParser.get().entity(todoDto);
		validate(todo);

		Long id = dao.insert(todo);
		statusService.insert(id, StatusEnum.TODO, loggedMail);
	}

	public List<TodoDTO> getAll() {
		return dao.getAll().stream().map(TodoParser.get()::dto).collect(Collectors.toList());
	}

	public void delete(Long id) {
		if (dao.findById(id) == null) {
			throw new NotFoundException();
		}
		dao.delete(id);
	}

	public TodoDTO find(Long id) {
		return TodoParser.get().dto(FindByID(id));
	}

	@Transactional(rollbackOn = Exception.class)
	public void update(Long id, TodoDTO dto, String loggedMail) {
		Todo todo = TodoParser.get().entity(dto);
		Todo todoData = FindByID(id);
		todoData.setName(todo.getName());
		dao.update(todoData);
		statusService.update(id, dto.getStatus(), loggedMail);
	}

	private Todo FindByID(Long id) {
		Todo todo = dao.findById(id);
		if (todo == null) {
			throw new NotFoundException();
		}
		return todo;
	}

}
