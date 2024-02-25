package com.plugins

import com.controllers.CategoryController
import com.controllers.OrderController
import com.controllers.ProductController
import com.controllers.UserController
import com.models.OrderRequest
import com.models.User
import com.repository.OrderRepositoryImpl
import com.repository.UserRepositoryImpl
import com.utils.HashingUtil

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {

    val userController = UserController(UserRepositoryImpl())
    // Initialize OrderController with necessary repositories
    val orderController = OrderController(OrderRepositoryImpl(), UserRepositoryImpl())


    routing {
        get("/") {
            call.respondText("Shop API", contentType = ContentType.Text.Plain)
        }

        // for products
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

        // for categories
        get("/categories/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val category = CategoryController().getCategory(id)
                if (category != null) {
                    call.respond(category)
                } else {
                    call.respondText("Category not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID format", status = HttpStatusCode.BadRequest)
            }
        }

        get("/categories") {
            val categories = CategoryController().getAllCategories()
            call.respond(categories)
        }

        get("/categories/{categoryId}/products") {
            val categoryId = call.parameters["categoryId"]?.toIntOrNull()
            if (categoryId != null) {
                val products = ProductController().getProductsByCategoryId(categoryId)
                if (products.isNotEmpty()) {
                    call.respond(products)
                } else {
                    call.respondText("No products found for this category", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid category ID format", status = HttpStatusCode.BadRequest)
            }
        }

        post("/register") {
            val userRequest = call.receive<UserRequest>()
            val createdUser = userController.createUser(
                User(
                    id = 0, // Ignored for creation as it's auto-generated.
                    email = userRequest.email,
                    hashedPassword = HashingUtil.hashPassword(userRequest.password),
                    firstName = null,
                    lastName = null,
                    address = null
                )
            )
            if (createdUser != null) {
                call.respond(HttpStatusCode.Created, UserResponse.fromUser(createdUser))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "User creation failed")
            }
        }


        post("/login") {
            val loginRequest = call.receive<UserLoginRequest>()
            val user = userController.validateUser(loginRequest.email, loginRequest.password)
            if (user != null) {
                call.respond(HttpStatusCode.OK, UserResponse.fromUser(user))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }


        post("/update-profile") {
            val userUpdateRequest = call.receive<UserUpdateRequest>()
            val currentUser = userController.validateUser(userUpdateRequest.email, userUpdateRequest.oldPassword)

            if (currentUser == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid old password")
            } else {

                val updatedUser = userController.updateUser(
                    User(
                        id = currentUser.id,
                        email = userUpdateRequest.email,
                        hashedPassword = HashingUtil.hashPassword(userUpdateRequest.newPassword),
                        firstName = userUpdateRequest.firstName,
                        lastName = userUpdateRequest.lastName,
                        address = userUpdateRequest.address
                    )
                )

                if (updatedUser != null) {
                    call.respond(HttpStatusCode.OK, UserResponse.fromUser(updatedUser))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User profile update failed")
                }
            }
        }

        post("/submit-order") {
            val orderRequest = call.receive<OrderRequest>()
            try {
                // Attempt to process the order
                val orderResponse = orderController.processOrder(orderRequest)
                if (orderResponse.success) {
                    // If successful, respond with OK status and the order response
                    call.respond(HttpStatusCode.OK, orderResponse)
                } else {
                    // If not successful, respond with BadRequest and include a message if available
                    call.respond(HttpStatusCode.BadRequest, "Order processing failed: ${orderResponse.message}")
                }
            } catch (e: Exception) {
                // Log and respond with error if there's an exception
                application.log.error("Failed to process order", e)
                call.respond(HttpStatusCode.InternalServerError, "Server error occurred while processing the order.")
            }
        }

    }
}

@Serializable
data class UserRequest(val email: String, val password: String)

@Serializable
data class UserLoginRequest(val email: String, val password: String)

@Serializable
data class UserUpdateRequest(
    val email: String,
    val newPassword: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val oldPassword: String
)



