import MyPostgresDriver.api._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
//import slick.driver.H2Driver.api._

object TrySlick extends App {
  val db = Database.forConfig("postgres")
  try {

    // The query interface for the Suppliers table
    val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

    // the query interface for the Coffees table
    val coffees: TableQuery[Coffees] = TableQuery[Coffees]

    val f:Future[Any] = ???

    Await.result(f, Duration.Inf)

  } finally db.close
  
}
