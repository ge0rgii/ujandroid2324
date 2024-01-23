package com.plugins

import com.module
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class RoutingKtTest {

    @Test
    fun testGetProductsId() = testApplication {
        application {
            module()
        }
        client.get("/products/2").apply {
            "Test 2 passed"
        }
    }
}