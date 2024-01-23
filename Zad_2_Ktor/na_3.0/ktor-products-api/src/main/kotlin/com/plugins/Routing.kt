package com.plugins

import com.controllers.ProductController

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/products/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val product = ProductController().getProduct(id)
                if (product != null) {
                    call.respond(product)
                } else {
                    call.respondText("Product not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID format", status = HttpStatusCode.BadRequest)
            }
        }

        get("/products") {
            val products = ProductController().getAllProducts()
            call.respond(products)
        }
    }
}
