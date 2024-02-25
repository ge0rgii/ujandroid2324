package com.repository

import com.database.Users
import com.models.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {
    override fun createUser(user: User): User? = transaction {
        val insertedId = Users.insert {
            it[email] = user.email
            it[hashedPassword] = user.hashedPassword
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[address] = user.address
        } get Users.id

        insertedId?.let { findUserById(it) }
    }

    override fun findUserByEmail(email: String): User? = transaction {
        Users.select { Users.email eq email }
            .mapNotNull { toUser(it) }
            .singleOrNull()
    }

    override fun updateUser(user: User): User? = transaction {
        val updatedCount = Users.update({ Users.id eq user.id }) {
            it[email] = user.email
            it[hashedPassword] = user.hashedPassword
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[address] = user.address
        }

        if (updatedCount > 0) findUserById(user.id) else null
    }

    private fun findUserById(id: Int): User? = Users.select { Users.id eq id }
        .mapNotNull { toUser(it) }
        .singleOrNull()

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            email = row[Users.email],
            hashedPassword = row[Users.hashedPassword],
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            address = row[Users.address]
        )
}
