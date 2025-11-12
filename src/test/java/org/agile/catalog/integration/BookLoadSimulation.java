package org.agile.catalog.integration;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BookLoadSimulation extends Simulation {

    // Defines base HTTP protocol
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // your local running Spring Boot app
            .acceptHeader("application/json");

    // Define scenario
    ScenarioBuilder scn = scenario("Book API Load Test")
            .exec(
                    http("Search Books")
                            .get("/api/books/search?keyword=java")
                            .check(status().is(200))
            );

    // Setup load profile
    {
        setUp(
                scn.injectOpen(atOnceUsers(100)) // 100 concurrent requests
        ).protocols(httpProtocol);
    }
}
