package br.com.stefanini.maratonadev.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "todo")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_TODOS", query = ""
				+ "SELECT id, name, CreatedAt FROM todo", resultClass = Todo.class),
		@NamedNativeQuery(name = "INSERT_TODO", query = ""
				+ "INSERT INTO todo (name, CreatedAt) values " + "(:name, :CreatedAt)"),
		@NamedNativeQuery(name = "DELETE_TODO", query = "DELETE todo WHERE id = :id"),
		@NamedNativeQuery(name = "HAS_NAME_TASK", query = ""
				+ "SELECT id, name, CreatedAt FROM todo where name like :name", resultClass = Todo.class),
		@NamedNativeQuery(name = "HAS_NAME", query = ""
				+ "SELECT id, name, CreatedAt FROM todo where id = :id", resultClass = Todo.class),
		@NamedNativeQuery(name = "UPDATE_TODO", query = "UPDATE todo "
				+ "set name = :name, CreatedAt = :CreatedAt WHERE id = :id"), })
public class Todo extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 250, nullable = false)
	private String name;

	@Column(name = "CreatedAt", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime CreatedAt;

	@OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TodoStatus> status;

	public Todo(Long id) {
		this.id = id;
	}

	public Todo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(LocalDateTime CreatedAt) {
		this.CreatedAt = CreatedAt;
	}

	public List<TodoStatus> getStatus() {
		return status;
	}

	public void setStatus(List<TodoStatus> status) {
		this.status = status;
	}

}
