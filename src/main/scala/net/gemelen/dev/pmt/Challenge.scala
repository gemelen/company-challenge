package net.gemelen.dev.pmt

import net.gemelen.dev.pmt.server.Service

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import org.http4s.blaze.server.BlazeServerBuilder

object Challenge extends IOApp {

  val httpApp = BlazeServerBuilder[IO]
    .bindLocal(8080)
    .withHttpApp(Service.total)
    .serve
    .compile
    .drain
    .as(ExitCode.Success)

  override def run(args: List[String]): IO[ExitCode] = httpApp

}
