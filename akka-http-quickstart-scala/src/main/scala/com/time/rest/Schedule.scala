package com.time.rest

import io.circe._, io.circe.generic.semiauto._

sealed trait EventType
object EventType {
  implicit val decodeEventType: Decoder[EventType] = Decoder[String].emap {
    case "open"  => Right(Open)
    case "close" => Right(Close)
    case other   => Left(s"invalid  event $other")
  }
}
case object Open extends EventType
case object Close extends EventType

case class TimeEvent(`type`: EventType, value: Int)
object TimeEvent {
  implicit val timeEventDecoder: Decoder[TimeEvent] = deriveDecoder[TimeEvent]
}
case class Schedule(
    monday: Seq[TimeEvent],
    tuesday: Seq[TimeEvent],
    wednesday: Seq[TimeEvent],
    thursday: Seq[TimeEvent],
    friday: Seq[TimeEvent],
    saturday: Seq[TimeEvent],
    sunday: Seq[TimeEvent]
)
object Schedule {
  implicit val scheduleDecoder: Decoder[Schedule] = deriveDecoder[Schedule]
  def apply(
      monday: Seq[TimeEvent],
      tuesday: Seq[TimeEvent],
      wednesday: Seq[TimeEvent],
      thursday: Seq[TimeEvent],
      friday: Seq[TimeEvent],
      saturday: Seq[TimeEvent],
      sunday: Seq[TimeEvent]
  ) = new Schedule(
    monday.sortBy(_.value),
    tuesday.sortBy(_.value),
    wednesday.sortBy(_.value),
    thursday.sortBy(_.value),
    friday.sortBy(_.value),
    saturday.sortBy(_.value),
    sunday.sortBy(_.value)
  )
}
