package net.gemelen.dev.pmt.server

import net.gemelen.dev.pmt.math.Solver
import net.gemelen.dev.pmt.server.InputDecoder

import cats.effect.IO
import io.circe._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._

object Service {

  val routes =
    HttpRoutes
      .of[IO] {
        // we have only one route effectively
        case req @ POST -> Root / "upload" / IntVar(number) =>
          implicit def csvDecoder = InputDecoder.csvDecoder[IO]
          req
            .decodeStrict[List[List[Int]]] { data =>
              val answers = data.map(Solver.solve(_, number))
              Ok(answers.asJson)
            }
      }

  val total = routes.orNotFound
}
