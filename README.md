# Nim REST Service

A lightweight Quarkus Kotlin application that lets you play the *misère* variant of the classic **Nim** game against the computer via REST.

---

## Features

* **Two strategies**  
  * *Random* – computer removes a random number of matches  
  * *Smart* – computer plays the mathematically optimal move
* Supports **multiple concurrent games** (each identified by a UUID)
* **Self‑contained** build – produces an *uber‑jar* (`java -jar …`)

---

## Build

```bash
./gradlew clean build
```

The build produces `build/quarkus-app/quarkus-run.jar`.

---

## Run

```bash
java -jar build/quarkus-app/quarkus-run.jar
```

Quarkus starts on <http://localhost:8080>.

---

## REST API

| Action | Verb / Path | Request body                     | Notes |
|--------|-------------|----------------------------------|-------|
| **Start new game** | `POST /games` | `{ "mode": "smart" }` (optional) | returns `201 Created` + JSON |
| **Get game state** | `GET  /games/{id}` | –                                | returns current matches + status |
| **Make a move** | `POST /games/{id}/moves` | `{ "taken": 2 }`                 | player takes 1‑3 matches; response contains computer move and new state |

### Example session (cURL)

```bash
# 1. Create smart game
ID=$(curl -s -H "Content-Type: application/json" -d '{ "mode": "smart" }' \
  http://localhost:8080/games | jq -r .id)

# 2. Player takes 2 matches
curl -s -H "Content-Type: application/json" -d '{ "taken": 2 }' \
  http://localhost:8080/games/$ID/moves | jq .
```

---

## Testing

```bash
./gradlew test
```

The test suite covers:

* Game logic (`GameService`)  
* Strategy selection (`ComputerStrategyFactory`)  
* Repository (`InMemoryGameStateStore`)  
* End‑to‑end HTTP flow (`GameControllerIT`)

---

## Project Structure

```
nim-rest
 ├─ api/            REST controller + DTOs
 ├─ application/    GameService, StrategyFactory
 ├─ domain/         GameState, GameMode, strategies
 ├─ infrastructure/ InMemoryGameStateStore
 └─ build.gradle.kts
```

---

## License

MIT – do whatever you want, just don’t blame me if you lose at Nim. 😉
