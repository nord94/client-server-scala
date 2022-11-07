import java.net.{InetAddress, Socket}
import java.io.{BufferedReader, InputStreamReader, ObjectInputStream, ObjectOutputStream, PrintWriter}

class ClientSoc {
  val host: InetAddress = InetAddress.getLocalHost()

  var clientSocket: Socket = null
  var out: PrintWriter = null
  var in: BufferedReader = null

  def startConnection(ip: InetAddress = host, port: Int = 1234): Unit = {
    clientSocket = new Socket(ip, port)
    out = new PrintWriter(clientSocket.getOutputStream(), true)
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

  }

  def sendMessage(msg: String): String = {
    out.println(msg);
    in.readLine()
  }

  def stopConnection(): Unit = {
    in.close();
    out.close();
    clientSocket.close();
  }

  def enterApp(): Unit = {
    print("Enter username: ")
    var username: String = ""
    while ((username != "") & (ServerSoc.addPerson(username) != "User successfully added")) {
      println("Enter another username: ")
      username = io.StdIn.readLine()
    }
        while (true) {
          println("1 - Enter chat, 2 - Close application")
          scala.io.StdIn.readLine() match {
            case "1" => {
              //        implement chat entering with loading history
            }
            case "2" => System.exit(0)
            case  _ =>
          }
        }

  }
}
