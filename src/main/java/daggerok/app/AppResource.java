package daggerok.app;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Path("")
@Stateless
@Produces(APPLICATION_JSON)
public class AppResource {

  @Inject SimpleChat chat;
  @Inject JsonService jsonService;

  @GET
  @Path("")
  public Response get(@Context final UriInfo uriInfo) {
    return Response.ok(jsonService.jsongify(uriInfo, AppResource.class, "get")
                                  .build())
                   .build();
  }

  @POST
  @Path("")
  @SneakyThrows
  public Response post(final JsonObject request) {
    final String message = request.getString("message");
    log.info("sending: {}", message);
    chat.send(message);
    return Response.accepted().build();
  }
}
