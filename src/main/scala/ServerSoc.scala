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

  def addPerson(username: String): String = {
    if (isPersonExists(username)) {
      "User already exists."
    }
    else {
      print("\nEnter password: ")
      val query: String = s"""insert into chat.users (username, password) values ('$username', '${scala.io.StdIn.readLine()}')"""
      val con: Connection = connectToDb()
      con.createStatement().executeUpdate(query)
      con.close()
      "User successfully added"
    }
  }
  def enterChat(): Unit = {
    print("\u001b[2J")

  }
  def isPersonExists(name: String): Boolean = {
    try {
      val con: Connection = connectToDb(user, password)
      val stmt: Statement = con.createStatement()
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
