package com.time.timetable

sealed trait ScheduleError {
  def msg: String
}
case object UnclosedError extends ScheduleError {
  override def msg: String = "Missing closing segment"
}
case object OrderError extends ScheduleError {
  override def msg: String = "Mistmached opening hours"
}
