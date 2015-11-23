import MyPostgresDriver.api._
import slick.jdbc.meta.MTable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
//import slick.driver.H2Driver.api._

object Actions {
  // The query interface for the Suppliers table
  val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

  // the query interface for the Coffees table
  val coffees: TableQuery[Coffees] = TableQuery[Coffees]

  val dropDb = {
    val dropSuppliers = MTable.getTables(suppliers.baseTableRow.tableName).flatMap { x =>
      if (!x.isEmpty) suppliers.schema.drop
      else DBIO.successful(())
    }
    val dropCoffees = MTable.getTables(coffees.baseTableRow.tableName).flatMap { x =>
      if (!x.isEmpty) coffees.schema.drop
      else DBIO.successful(())
    }
    dropCoffees >> dropSuppliers
  }

  val createDb = (suppliers.schema ++ coffees.schema).create

  val initDb = dropDb >> createDb

  def withDb[A](action: DBIO[A]): A = {
    val db = Database.forConfig("postgres")
    try {

      val f = db.run(action)

      Await.result(f, Duration.Inf)

    } finally db.close
  }
}

object TrySlick extends App {
  val db = Database.forConfig("postgres")
  try {


    val f: Future[Any] = ???

    Await.result(f, Duration.Inf)

  } finally db.close

}
