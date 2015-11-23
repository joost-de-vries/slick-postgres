import MyPostgresDriver.api._

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

  val dropDb: DBIO[Unit] = (suppliers.schema ++ coffees.schema).drop

  val createDb: DBIO[Unit] = (suppliers.schema ++ coffees.schema).create


  val getSuppliersAboveLimit: DBIO[Seq[String]] = {
    (for {c <- coffees if c.price < 10.0} yield c.name).result
  }

  val getCoffeeNames = coffees.map(_.name)

  val getSortedLowPricedCoffeeNames = coffees.filter(x => x.price < 10.0).sortBy(_.name).map(_.name)

  val plainSqlQuery = {
    val limit = 10.0

    sql"select COF_NAME from COFFEES where PRICE < $limit".as[String]
  }

  try {

    val f = db.run(dropDb)

    Await.result(f, Duration.Inf)

  } finally db.close

}