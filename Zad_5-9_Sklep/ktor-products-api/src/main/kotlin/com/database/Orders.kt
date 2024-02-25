import com.database.Products
import com.database.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object Orders : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("userId").references(Users.id)
    val createdAt = datetime("createdAt").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

