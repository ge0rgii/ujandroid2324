package com

import com.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testGetProduct() = testApplication {
        application { module() }
        // Assuming there is a product with ID 1
        client.get("/products/1").apply {
            assertEquals(HttpStatusCode.OK, status)
            // Assert based on expected response
        }
    }

    @Test
    fun testGetAllProducts() = testApplication {
        application { module() }
        client.get("/products").apply {
            assertEquals(HttpStatusCode.OK, status)
            // Assert based on expected response
        }
    }
}
