package br.com.stefanini.maratonadev.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserDTO implements Serializable {

  private static final long serialVersionUID = 1l;

  private UUID id;

  @NotNull(message = "Name is required")
  private String name;

  @NotNull(message = "Email is required")
  @NotBlank(message = "Email could not be blank")
  @Email(message = "this is not a valid email")
  private String email;

  @NotNull(message = "Email is required")
  @NotBlank(message = "Email could not be blank")
  @Length(min = 6, message = "Passowrd must be at least 6 characters")
  private String password;

  @NotNull(message = "Role is required")
  @NotBlank(message = "Role could not be blank")
  private String Role;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return Role;
  }

  public void setRole(String Role) {
    this.Role = Role;
  }

}