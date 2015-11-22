import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

//import slick.driver.H2Driver.api._
import MyPostgresDriver.api._

object CaseClassMapping extends App {
  // the base query for the Users table
  val users = TableQuery[Users]

  val db = Database.forConfig("postgres")

  try {
    Await.result(db.run(DBIO.seq(
      // create the schema
      users.schema.create,

      // insert two User instances
      users += User(
        name="John Doe",
        roles=Set(Admin)
      ),
      users += User(
        name="Fred Smith",
        roles=Set(Guest)
      ),
      users += User(
        name="Jan de Vries",
        roles=Set(Admin, Customer)
      ),

      // print the users (select * from USERS)
      users.result.map(println)
    )), Duration.Inf)
  } finally db.close
}

case class User(name: String,
                id: Option[Int] = None,
                roles: Set[Role] = Set()
               )

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  // The name can't be null
  def name = column[String]("NAME")

  def roles = column[Set[Role]]("ROLES")
  // the * projection (e.g. select * ...) auto-transforms the tupled
  // column values to / from a User
  def * = (name, id.?,roles) <> (User.tupled, User.unapply)
}

sealed trait Role{
  def name: String
}

case object Admin extends Role{
  val name="Admin"
}

case object Guest extends Role{
  val name="Guest"
}

case object Customer extends Role{
  val name="Customer"
}

object Role {
  def apply(s: String): Role = s match {
    case Admin.name => Admin
    case Guest.name => Guest
    case Customer.name => Customer
  }

  def unapply(role: Role) = role.name
}
