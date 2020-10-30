package br.com.stefanini.maratonadev.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

public class TodoStatusDTO {

	private Long id;

	@JsonbDateFormat("dd/MM/yyyy HH:mm")
	private LocalDateTime data;

	private String status;

	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
