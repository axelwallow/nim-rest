package de.axelwallow.nim.api

import de.axelwallow.nim.api.dto.GameRequest
import de.axelwallow.nim.api.dto.GameResponse
import de.axelwallow.nim.api.dto.MoveRequest
import de.axelwallow.nim.api.dto.MoveResponse
import de.axelwallow.nim.application.GameService
import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.GameState
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.ResponseStatus
import java.util.*

@ApplicationScoped
@Path("/games")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class GameController(
    private val gameService: GameService,
) {

    @POST
    @ResponseStatus(201)
    fun newGame(req: GameRequest?): GameResponse {
        val mode = GameMode.Companion.find(req?.mode)
        val game = gameService.createGame(mode)
        return game.toGameDto()
    }

    @GET
    @Path("/{id}")
    fun getGame(@PathParam("id") id: UUID): GameResponse =
        gameService.getGame(id)
            .toGameDto()

    @POST
    @Path("/{id}/moves")
    fun makeMove(@PathParam("id") id: UUID, req: MoveRequest): MoveResponse =
        gameService.makeMove(id, req.taken)
            .toMoveDto()

    private fun GameState.toGameDto() = GameResponse(id, matches, status)
    private fun GameState.toMoveDto() = MoveResponse(id, matches, status, lastComputerMove)
}