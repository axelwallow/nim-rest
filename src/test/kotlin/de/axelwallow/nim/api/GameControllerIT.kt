package de.axelwallow.nim.api

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

@QuarkusTest
class GameControllerIT {

    @Test
    fun `start game returns 201 and gameId`() {
        given()
            .contentType(ContentType.JSON)
            .body("""{ "smart": true }""")
            .`when`()
            .post("/games")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("matches", equalTo(13))
            .body("status", equalTo("RUNNING"))
    }

    @Test
    fun `player move reduces matches and returns cpu move`() {
        val gameId: String = given()
            .contentType(ContentType.JSON)
            .body("""{ "mode": "random" }""")
            .`when`()
            .post("/games")
            .then()
            .statusCode(201)
            .extract()
            .path("id")

        given()
            .contentType(ContentType.JSON)
            .body("""{ "taken": 1 }""")
            .`when`()
            .post("/games/$gameId/moves")
            .then()
            .statusCode(200)
            .body("id", equalTo(gameId))
            .body("matches", lessThan(13))
            .body("status", equalTo("RUNNING"))
            .body("lastComputerMove", greaterThanOrEqualTo(1))
    }
}