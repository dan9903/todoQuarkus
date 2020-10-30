package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;

public class UserParser {

  public static UserParser get() {
    return new UserParser();
  }

  public UserDTO dto(User entity) {
    UserDTO dto = new UserDTO();

    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setEmail(entity.getEmail());
    dto.setPassword(entity.getPassword());
    dto.setRole(entity.getRole());

    return dto;
  }

  public User entity(UserDTO dto) {
    User entity = new User();

    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setRole(dto.getRole());

    return entity;
  }

}