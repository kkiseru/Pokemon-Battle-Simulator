package my.com.seru.util
import scalikejdbc._
import my.com.seru.model.Player

//code referenced by Dr Chin Teck Min
trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";

  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "Seru", "ILoveTeegoos")
  implicit val session = AutoSession


}
object Database extends Database{
  def setupDB() = {
    if (!hasDBInitialize)
      Player.initializeTable()
  }
  def hasDBInitialize : Boolean = {

    DB getTable "Player" match {
      case Some(x) => true
      case None => false
    }

  }
}
