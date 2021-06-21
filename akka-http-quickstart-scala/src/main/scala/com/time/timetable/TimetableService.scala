package com.time.timetable

import com.time.rest.Schedule
import com.time.rest.TimeEvent
import com.time.rest.Close
import scala.util.chaining._
import cats.data.Op
import com.time.rest.Open

trait TimetableService {
  def parseTimetable(schedule: Schedule): Either[ScheduleError, TimeTable]
}
class TimetableServiceImpl() extends TimetableService {

  def parseTimetable(schedule: Schedule): Either[ScheduleError, TimeTable] = {
    val m = parseDay(schedule.monday, schedule.tuesday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val t = parseDay(schedule.tuesday, schedule.wednesday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val w = parseDay(schedule.wednesday, schedule.thursday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val th = parseDay(schedule.thursday, schedule.friday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val f = parseDay(schedule.friday, schedule.saturday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val sa = parseDay(schedule.saturday, schedule.sunday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    val su = parseDay(schedule.sunday, schedule.monday.headOption) match {
      case Left(value)  => return Left(value)
      case Right(value) => value
    }
    Right(TimeTable(m, t, w, th, f, sa, su))
  }

  private def parseDay(
      events: Seq[TimeEvent],
      closing: Option[TimeEvent]
  ): Either[ScheduleError, Seq[Range]] = {

    val seq = events match {
      case x :: rest if x.`type` == Close => rest
      case n @ Nil                        => Seq()
      case y                              => y
    }

    seq
      .grouped(2)
      .map {
        case x :: y :: Nil =>
          if (x.`type` == Close || y.`type` == Open)
            return Left(OrderError)
          else Range(x.value, y.value)
        case x :: Nil =>
          closing
            .map(v =>
              if (v.`type` == Open)
                return Left(OrderError)
              else Range(x.value, v.value)
            )
            .getOrElse(return Left(UnclosedError))
      }
      .toSeq
      .pipe(Right(_))
  }
}
