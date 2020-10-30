package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.TodoStatusDAO;
import br.com.stefanini.maratonadev.dto.TodoStatusDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.TodoStatus;
import br.com.stefanini.maratonadev.model.dominio.StatusEnum;
import br.com.stefanini.maratonadev.model.parser.TodoStatusParser;

@RequestScoped
public class TodoStatusService {
	@Inject
	TodoStatusDAO dao;

	@Inject
	UserService userService;

	private void validate(TodoStatus todoStatus) {
		if (StatusEnum.isInvalido(todoStatus.getStatus().toString())) {
			throw new NotFoundException();
		}
	}

	private void validateUpdate(TodoStatus todoStatusBanco, TodoStatus todoStatusTela) {
		validate(todoStatusTela);
		if (todoStatusBanco.getStatus().equals(StatusEnum.DONE)) {
			throw new NotAllowedException("Tarefa com status que não permite modificação");
		}

	}

	@Transactional(rollbackOn = Exception.class)
	public void insert(Long id, StatusEnum enumValue, String loggedMail) {
		TodoStatus status = new TodoStatus(enumValue);
		status.setTodo(new Todo(id));
		status.setUser(userService.findUserByEmail(loggedMail));
		validate(status);
		dao.insert(status);

	}

	@Transactional(rollbackOn = Exception.class)
	public void update(Long id, String enumValue, String loggedMail) {
		TodoStatus statusTela = new TodoStatus(StatusEnum.valueOf(enumValue));
		statusTela.setTodo(new Todo(id));
		TodoStatus statusBanco = dao.findStatusByTodo(id).get(0);
		validateUpdate(statusBanco, statusTela);

		statusTela.setTodo(new Todo(id));
		statusTela.setUser(userService.findUserByEmail(loggedMail));

		dao.insert(statusTela);
	}

	public List<TodoStatusDTO> findStatusByTodoID(Long idTask) {
		List<TodoStatus> statusBanco = dao.findStatusByTodo(idTask);
		return statusBanco.stream().map(TodoStatusParser.get()::dto).collect(Collectors.toList());

	}
}
