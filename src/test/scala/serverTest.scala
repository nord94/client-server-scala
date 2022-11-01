import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, ResultSet, Statement}

class serverTest extends AnyFunSuite {
  val url: String = "jdbc:postgresql://localhost/client-server"
  val user: String = "test"
  val password: String = "test"
  val queryAdd: String = """insert into chat.users (username, password) values ('name', 'password')"""
  val queryDelete: String = """delete from chat.users where username = 'name' """
  val tstCon: Connection = server.connect(user, password)
  val stmt: Statement = tstCon.createStatement()
  test("server.ifPersonExists") {
    assert(!server.isPersonExists("name"))
    stmt.executeUpdate(queryAdd)
    assert(server.isPersonExists("name"))
    stmt.executeUpdate(queryDelete)
    assert(!server.isPersonExists("name"))
    tstCon.close()
  }
}
