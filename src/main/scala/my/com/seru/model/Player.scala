package my.com.seru.model
import my.com.seru.util.Database
import scalikejdbc._
import scalafx.beans.property.{ObjectProperty, StringProperty}

import scala.util.Try

//code referenced by Dr Chin Teck Min
class Player (val username : String, var Score : Int) extends Database {
  def this() = this( "", 0)

  var userName = new StringProperty(username)
  var score = ObjectProperty(Score)

  def save(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
					insert into Player (username,score) values
						(${userName.value}, ${score.value})
				""".update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update Player
				set
				score   = ${score.value}
				where username  = ${userName.value}
				""".update.apply()
      })
    }
  }
  def isExist: Boolean = {
    DB readOnly { implicit session =>
      sql"""
      select * from Player where
      username = ${userName.value}
    """.map(rs => rs.string("username")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }

}
object Player extends Database {
  var userName = new StringProperty()
  var score = ObjectProperty(0)

  def initializeTable() : Unit = {
      DB autoCommit { implicit session =>
        sql"""
      create table Player (
           id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
           username varchar(64),
           score int)
      """.execute.apply()
      }
  }
  def save(): Try[Int] = {

    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
					insert into Player (username,score) values
						(${userName.value}, ${score.value})
				""".update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update Player
				set
				score   = ${score.value}
				where username  = ${userName.value}
				""".update.apply()
      })
    }
  }
    def isExist: Boolean = {
      DB readOnly { implicit session =>
        sql"""
				select * from Player where
				username = ${userName.value}
			""".map(rs => rs.string("username")).single.apply()
      } match {
        case Some(x) => true
        case None => false
      }

    }

}
