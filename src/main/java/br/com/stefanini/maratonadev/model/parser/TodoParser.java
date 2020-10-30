package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.model.Todo;

public class TodoParser {

	public static TodoParser get() {
		return new TodoParser();
	}

	public Todo entity(TodoDTO dto) {
		Todo entity = new Todo();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCreatedAt(dto.getCreatedAt());

		return entity;
	}

	public TodoDTO dto(Todo entity) {

		TodoDTO dto = new TodoDTO();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCreatedAt(entity.getCreatedAt());

		dto.setStatus(entity.getStatus().get(0).toString());

		return dto;
	}
}
