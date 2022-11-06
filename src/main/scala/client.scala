import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import scala.annotation.tailrec
import scala.util.control.Breaks.break;

object client extends App {
  val host: InetAddress = InetAddress.getLocalHost()
  val socket: Socket  = null;
  val oos: ObjectOutputStream = null;
  val ois: ObjectInputStream = null;

  @tailrec
  def loop(x: Int): Unit = {
    if (x > 1) {
      val socket: Socket = new Socket(host.getHostName(), 9876)
      val oos: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream())
      println("Sending request to Socket Server")
      oos.writeObject(""+x)
      val ois: ObjectInputStream = new ObjectInputStream(socket.getInputStream());
      val message: String =  ois.readObject().toString;
      println("Message: " + message);
      ois.close()
      oos.close()
      Thread.sleep(100)
      loop(x - 1)
    }
    else oos.writeObject("exit")
  }
  loop(5)
}
