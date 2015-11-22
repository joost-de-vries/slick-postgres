import java.time.LocalDateTime

import com.github.tminglei.slickpg._

import scala.util.Try

trait MyPostgresDriver extends ExPostgresDriver
with PgArraySupport
with PgDateSupport
with PgDate2Support
with PgRangeSupport
with PgHStoreSupport
with PgPlayJsonSupport
with PgSearchSupport
//with PgPostGISSupport
with PgNetSupport
with PgLTreeSupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits
  with DateTimeImplicits
  with Date2DateTimePlainImplicits
  with JsonImplicits
  with NetImplicits
  with LTreeImplicits
  with RangeImplicits
  with HStoreImplicits
  with SearchImplicits
  with SearchAssistants {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)


    implicit val roleListColumnType: DriverJdbcType[List[Role]] = new SimpleArrayJdbcType[Role]("text")
      .basedOn[String](_.name, Role(_)).to(_.toList)

    implicit val roleSetColumnType =
      MappedColumnType.base[Set[Role], List[Role]]({ roleSet => roleSet.toList }, { roleList => roleList.toSet })

    // Mapping of Enumerations
    def enumValueMapper(enum: Enumeration) = MappedColumnType.base[enum.Value, String](
      e => e.toString,
      s => Try(enum.withName(s)).getOrElse(throw new IllegalArgumentException
      (s"enumeration $s doesn't exist $enum[${enum.values.mkString(",")}]"))
    )

    def enumIdMapper(enum: Enumeration) = MappedColumnType.base[enum.Value, Int](
      e => e.id,
      i => enum.apply(i)
    )

//    implicit val importanceMapper = enumValueMapper(Importance)



//    implicit val eventColumnType = MappedColumnType.base[Event, JsValue](
//    { evt => Json.toJson(evt) }, { jsValue => {
//      val value: Event = Json.fromJson(jsValue) match {
//        case JsSuccess(s, _) => s
//        case JsError(errors) => {
//          Logger.error(s"JSON [$jsValue]not valid. Errors: $errors.")
//          throw new IllegalStateException();
//        }
//      }
//      value
//    }
//    })
    }

}

object MyPostgresDriver extends MyPostgresDriver

