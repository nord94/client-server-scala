import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.{InetAddress, Socket}

object client_ex extends App{
  val hostName: String = InetAddress.getLocalHost().getHostName
  val port: Int = 1234

  try {
    val socket: Socket = new Socket(hostName, port)
    val out: PrintWriter = new PrintWriter(socket.getOutputStream, true)
    val in: BufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val stdIn: BufferedReader = new BufferedReader(new InputStreamReader(System.in))
  }

}
