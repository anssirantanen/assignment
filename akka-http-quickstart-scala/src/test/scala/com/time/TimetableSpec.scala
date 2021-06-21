package com.time

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.time.rest.TimetableRoutes
import com.time.timetable.TimetableServiceImpl
import com.time.timetable.TimeTableFormatter
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class TimetableSpec
    extends AnyWordSpec
    with Matchers
    with ScalaFutures
    with ScalatestRouteTest
    with OpeningData {
  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  val service = new TimetableServiceImpl()
  val formatter = new TimeTableFormatter()
  lazy val route = new TimetableRoutes(service, formatter).routes

  "Timetable" should {
    "provide a valid timetable" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, baseJson)

      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        val resp = responseAs[String]
        resp.stripMargin should ===(baseResponse)
      }
    }
    "handle unsorted events" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, baseJson)

      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        val resp = responseAs[String]
        resp.stripMargin should ===(baseResponse)
      }
    }
    "reject unclosed times" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, unclosed)

      request ~> route ~> check {
        status should ===(StatusCodes.BadRequest)
      }
    }
    "reject wrong sequence" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, unclosed)

      request ~> route ~> check {
        status should ===(StatusCodes.BadRequest)
      }
    }
    "handle sunday overflow" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, sunday)

      request ~> route ~> check {
        status should ===(StatusCodes.OK)
      }
    }
    "support multiple open times per day" in {
      val request =
        Post("/").withEntity(ContentTypes.`application/json`, multiple)

      request ~> route ~> check {
        status should ===(StatusCodes.OK)
        val resp = responseAs[String]
        resp.stripMargin should ===(multipleResp)
      }
    }
  }
}
