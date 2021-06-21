package com.time.rest

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import scala.util.chaining._

import scala.concurrent.Future
import akka.actor.ActorRef
import akka.actor.ActorSystem
import de.heikoseeberger.akkahttpcirce._
import com.time.timetable.TimetableServiceImpl
import com.time.timetable.TimetableService
import com.time.timetable.TimeTableFormatter
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ContentTypes._

class TimetableRoutes(
    service: TimetableService,
    formatter: TimeTableFormatter
)(implicit
    val system: ActorSystem
) {
  import FailFastCirceSupport._

  val routes: Route = pathSingleSlash {
    post {
      entity(as[Schedule]) { schedule: Schedule =>
        val table = service.parseTimetable(schedule)
        table
          .map(formatter.toText(_)) match {
          case Left(value) => complete(StatusCodes.BadRequest -> value.msg)
          case Right(value) =>
            val entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, value)
            val res = HttpResponse(200, entity = entity)
            complete(
              res
            )
        }
      }
    }
  }
}
