import Actions.Actions

import java.io.{BufferedReader, InputStreamReader, ObjectInputStream, ObjectOutputStream, PrintWriter}
import java.net.{ServerSocket, Socket}
import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement}
import scala.util.control.Breaks.break

object ServerSoc extends App{
  val user: String = "test"
  val password: String = "test"
  val portNumber: Int = 1234
  var in: BufferedReader = null
  var out: PrintWriter = null
  val serverSocket: ServerSocket = null
  val clientSocket :Socket = null

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
      val con: Connection = connectToDb()
      con.createStatement().executeUpdate(query)
      con.close()
      println(s"User (name = '$username', password = '$password') successfully added")
    }
  }
  def enterChat(): Unit = {
    print("\u001b[2J")

  }
  def isPersonExists(name: String): Boolean = {
    try {
      val con: Connection = connectToDb(user, password)
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
  def connectToDb(user: String = "test", password: String = "test"): Connection = {
    val url: String = "jdbc:postgresql://localhost/client-server"
    DriverManager.getConnection(url, user, password)
  }

  def start(port: Int = portNumber): Unit = {
    val serverSocket: ServerSocket = new ServerSocket(port);
    val clientSocket: Socket = serverSocket.accept();
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//    val inputLine: String = in.readLine()
    while (true) {
      in.readLine() match {
        case "." => out.println("good bye"); break;
        case "hello server" => out.println("hello client")
      }
    }
    stop()
  }

  def stop(): Unit ={
    in.close()
    out.close()
    clientSocket.close()
    serverSocket.close()
  }
  start()
}
