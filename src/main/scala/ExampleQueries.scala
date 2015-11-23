import MyPostgresDriver.api._
import slick.backend.DatabasePublisher

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
//import slick.driver.H2Driver.api._

object ExampleQueries extends App {
  val db = Database.forConfig("postgres")

    // The query interface for the Suppliers table
    val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

    // the query interface for the Coffees table
    val coffees: TableQuery[Coffees] = TableQuery[Coffees]
  try {

    val f:Future[Any] = ???

    Await.result(f, Duration.Inf)

  } finally db.close

  val  dropDb:DBIO[Unit] = (suppliers.schema ++ coffees.schema).create

  val createDb:DBIO[Unit] = (suppliers.schema ++ coffees.schema).drop

  val getSuppliersAboveLimit:DBIO[Seq[String]] = (for{c <- coffees if c.price < 10.0} yield c.name ).result
}
