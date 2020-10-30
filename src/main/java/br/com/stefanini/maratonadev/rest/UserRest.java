package br.com.stefanini.maratonadev.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.service.UserService;
import javax.validation.Validator;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRest {
  @Inject
  UserService service;

  @Inject
  Validator validator;

  @POST()
  @Path("/create")
  @Operation(summary = "Create a new user", description = "send all necessary information to register new user")
  @APIResponse(responseCode = "201", description = "user", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) })
  public Response create(UserDTO user) {
    Set<ConstraintViolation<UserDTO>> errors = validator.validate(user);

    if (errors.isEmpty()) {
      service.insert(user);
    }
    return Response.status(Response.Status.CREATED).build();
  }

  @GET()
  @Path("/control")
  @RolesAllowed("ADMIN")
  @Operation(summary = "Control all user information", description = "Control panel for administration")
  @APIResponse(responseCode = "200", description = "users", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })
  public Response getAll() {
    return Response.status(Response.Status.OK).entity(service.getAll()).build();
  }
}