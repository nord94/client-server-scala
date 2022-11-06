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


}
