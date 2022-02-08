package net.gemelen.dev.pmt.server

import cats.effect.IO
import munit.Http4sHttpRoutesSuite
import org.http4s._
import org.http4s.HttpRoutes
import org.http4s.client.dsl.io._
import org.http4s.dsl.io._
import org.http4s.syntax.all._

class ControllerSuite extends Http4sHttpRoutesSuite {

  override val routes: HttpRoutes[IO] = Service.routes

  val cssHeader = List(Header("Content-Type", "text/css"))
  val csvHeader = List(Header("Content-Type", "text/csv"))

  val csv     = """3,5,2,-4,8,11"""
  val csv2    = """3,5,2,-4,8,11
  3,5,2,-4,8,11"""
  val garbage = "a,1,-"

  test(GET(uri"/")).alias("Other paths return 404") { response => assertEquals(response.status.code, 404) }

  test(GET(uri"/upload/1")).alias("'/upload/{number}' doesn't exist") { response =>
    assertEquals(response.status.code, 404)
  }

  test(POST(uri"/upload/2", cssHeader)).alias("Content-Type of request is checked") { response =>
    assertEquals(response.status.code, 415)
  }

  test(POST(uri"/upload/2", csvHeader)).alias("Empty body is treated as empty list") { response =>
    assertEquals(response.status.code, 200)
    assertIO(response.as[String], "[]")
  }

  test(POST(garbage, uri"/upload/7", csvHeader)).alias("Invalid data results in templated JSON message") { response =>
    assertEquals(response.status.code, 400)
    assertIO(response.as[String], """"{\"code\": \"wrong.input.type\", \"message\": \"'a' is not a valid Int\"}"""")
  }

  test(POST(csv, uri"/upload/7", csvHeader)).alias("Answer found and provided") { response =>
    assertEquals(response.status.code, 200)
    assertIO(response.as[String], "[[[5,2],[-4,11]]]")
  }

  test(POST(csv, uri"/upload/6", csvHeader)).alias("Answer not found and not provided") { response =>
    assertEquals(response.status.code, 200)
    assertIO(response.as[String], "[[]]")
  }

  test(POST(csv2, uri"/upload/6", csvHeader)).alias("Multiline CSV is processed too") { response =>
    assertEquals(response.status.code, 200)
    assertIO(response.as[String], "[[],[]]")
  }
}
