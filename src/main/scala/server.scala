import scala.util.control.Breaks.break
import org.postgresql
import sun.security.util.Password

import java.sql
import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}

object server {
  val url: String = "jdbc:postgresql://localhost/client-server"
  val user: String = "test"
  val password: String = "test"

  def main(args: Array[String]) = {
    while (true) {
      println("1 - Add user, 2 - Enter chat, 3 - Close application")
      scala.io.StdIn.readLine() match {
        case "1" => {
          addPerson()
          //        implement adding user to database
        }
        case "2" => {
          //        implement chat entering with loading history
        }
        case "3" => System.exit(0)
        case  _ =>
      }
    }
  }

  def connect(user: String = "test", password: String = "test"): Connection = {
    val url: String = "jdbc:postgresql://localhost/client-server"
    DriverManager.getConnection(url, user, password)
  }

  def isPersonExists(name: String): Boolean = {
    try {
      val con: Connection = connect(user, password)
      val stmt: Statement = con.createStatement()
      val query: String = s"select 1 from chat.users where username = ${name}"
      val rs: ResultSet = stmt.executeQuery(s"select 1 from chat.users where username = '${name}'")
      rs.next() match {
        case true => true
        case false => false
        case _ => {
          con.close()
          false
        }
      }
    } catch {
      case e: SQLException =>
        System.out.println(e.getMessage)
        false
    }
  }


  def testIsPersonExists(): Unit = {

  }

  def addPerson(): Unit = {
    print("Enter username: ")
    val username: String = scala.io.StdIn.readLine()
    print("\nEnter password: ")
    val password: String = scala.io.StdIn.readLine()
    if (isPersonExists(username)) {
      println("person already exists")
    }
    else {
      val query: String = s"""insert into chat.users (username, password) values ('$username', '$password')"""
      val con: Connection = connect()
      con.createStatement().executeUpdate(query)
      con.close()
      println(s"User (name = '$username', password = '$password') successfully added")
    }
  }
}

object Role {
  val roles: List[String] = List("user", "admin")
}

abstract class Person (val id: Int, val name: String, val password: String){
  val role: String

  def isUserExists(person: Person): Boolean
//  def addPerson(user: User): (String, String)
}

class User(id: Int, name: String, password: String) extends Person(id, name, password) {
  val role:String = "user"

  def this(name: String, pwd: String) = this(id = 1, name=name, password = pwd)

  /*TODO
  check user existance in the database
   */
  def isUserExists(person: Person): Boolean = {
    false
  }
}