package com.time.timetable

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class TimeTableFormatter {
  def toText(table: TimeTable): String = {
    implicit val format = DateTimeFormatter.ofPattern("h:mm a")

    s"""Monday: ${openingParser(table.monday)}
    |Tuesday: ${openingParser(table.tuesday)}
    |Wednesday: ${openingParser(table.wednesday)}
    |Thursday: ${openingParser(table.thursday)}
    |Friday: ${openingParser(table.friday)}
    |Saturday: ${openingParser(table.saturday)}
    |Sunday: ${openingParser(table.sunday)}""".stripMargin
  }
  private def openingParser(
      times: Seq[Range]
  )(implicit format: DateTimeFormatter): String = {
    if (times.isEmpty) {
      "Closed"
    } else {
      times
        .map(r => s"${timeParser(r.open)} - ${timeParser(r.close)}")
        .mkString(", ")
    }
  }
  private def timeParser(epoch: Int)(implicit
      format: DateTimeFormatter
  ): String =
    LocalDateTime
      .ofEpochSecond(epoch, 0, ZoneOffset.UTC)
      .format(format)
      .replace(":00", "")
}
