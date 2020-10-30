package br.com.stefanini.maratonadev.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.service.TodoService;

@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "USER", "ADMIN" })
public class TodoRest {

	@Inject
	TodoService service;

	@Inject
	Validator validator;

	@GET
	@Path("")
	@Operation(summary = "List all tasks", description = "Returns a lists of Todo.class")
	@APIResponse(responseCode = "200", description = "todo list", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class, type = SchemaType.ARRAY)) })
	public Response getAll() {

		return Response.status(Response.Status.OK).entity(service.getAll()).build();
	}

	@POST
	@Path("")
	@Operation(summary = "Includes a task", description = "Includes a task")
	@APIResponse(responseCode = "201", description = "task", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response create(TodoDTO todo, @Context SecurityContext securityContext) {

		Set<ConstraintViolation<TodoDTO>> errors = validator.validate(todo);

		if (errors.isEmpty()) {
			service.insert(todo, securityContext.getUserPrincipal().getName());
		} else {
			List<String> errorsList = errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
			throw new NotFoundException(errorsList.get(0));

		}

		return Response.status(Response.Status.CREATED).build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed("ADMIN")
	@Operation(summary = "Deletes a task", description = "Deletes a task")
	@APIResponse(responseCode = "202", description = "task", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response delete(@PathParam("id") Long id) {
		service.delete(id);
		return Response.status(Response.Status.ACCEPTED).build();
	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Find task by ID", description = "Find task by ID")
	@APIResponse(responseCode = "200", description = "task", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response findByID(@PathParam("id") Long id) {
		return Response.status(Response.Status.OK).entity(service.find(id)).build();
	}

	@PUT
	@Path("{id}")
	@Operation(summary = "update a task based on id", description = "update a task based on id")
	@APIResponse(responseCode = "200", description = "task", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response update(@PathParam("id") Long id, TodoDTO todo, @Context SecurityContext securityContext) {
		service.update(id, todo, securityContext.getUserPrincipal().getName());
		return Response.status(Response.Status.OK).build();
	}
}
