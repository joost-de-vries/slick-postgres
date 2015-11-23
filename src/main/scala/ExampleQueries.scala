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

  val insertSuppliers: DBIO[Int] = {
    suppliers += ((101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")),
    suppliers += ((49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")),
    suppliers += ((150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966"))
  }

  val insertCoffees = {
    coffees ++= Seq (
      ("Colombian",         101, 7.99, 0, 0),
      ("French_Roast",       49, 8.99, 0, 0),
      ("Espresso",          150, 9.99, 0, 0),
      ("Colombian_Decaf",   101, 8.99, 0, 0),
      ("French_Roast_Decaf", 49, 9.99, 0, 0)
    )
  }

  try {

    val f = db.run(dropDb)

    Await.result(f, Duration.Inf)

  } finally db.close

}