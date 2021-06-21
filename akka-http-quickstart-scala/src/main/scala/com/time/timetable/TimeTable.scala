package com.time.timetable

case class Range(open: Int, close: Int)

case class TimeTable(
    monday: Seq[Range],
    tuesday: Seq[Range],
    wednesday: Seq[Range],
    thursday: Seq[Range],
    friday: Seq[Range],
    saturday: Seq[Range],
    sunday: Seq[Range]
)
