import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

object testConnection extends App{
  val url: String = "jdbc:postgresql://localhost/client-server"
  val user: String = "test"
  val password: String = "test"

  try {
    val con: Connection = DriverManager.getConnection(url, user, password)
    val stmt: Statement = con.createStatement()
    val rs: ResultSet = stmt.executeQuery("""select username, password, id from chat.users""")

    println("Connected to the PostgreSQL server successfully.")

    while (rs.next()) {
//      println(rs.getInt("id") + )
      println(
        s"""id:  \t\t"${rs.getInt("id")}",
           |username:\t "${rs.getString("username")}",
           |password:\t "${rs.getString("password")}" \n""".stripMargin)
    }
    con.close()
  } catch {
    case e: SQLException =>
      System.out.println(e.getMessage)
  }


}
