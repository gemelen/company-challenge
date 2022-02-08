package net.gemelen.dev.pmt.server

import cats.data.EitherT
import cats.effect.kernel.Concurrent
import cats.implicits._
import io.circe._
import io.circe.syntax._
import kantan.csv._
import kantan.csv.cats._
import kantan.csv.ops._
import org.http4s._

object InputDecoder {

  /**
    * Translates CSV parsing error into expected format.
    */
  private def transformError(re: ReadError): DecodeFailure = new DecodeFailure() {

    override def message: String = re.getMessage()

    override def cause: Option[Throwable] = re.getCause().some

    override def toHttpResponse[F[_]](httpVersion: HttpVersion): Response[F] = {
      Response(Status.BadRequest, httpVersion)
        .withEntity(s"""{"code": "wrong.input.type", "message": "${this.message}"}""".asJson.show)(
          EntityEncoder.stringEncoder[F]
        )
    }
  }

  /**
   * Creates http4s `Entity` decoder for a data of List[List[Int]] nature, presented in a CSV format. Uses `kantan` CSV
   * parser.
   */
  def csvDecoder[F[_]](
      implicit
      ev: Concurrent[F]
  ): EntityDecoder[F, List[List[Int]]] =
    EntityDecoder.decodeBy[F, List[List[Int]]](MediaType.text.csv) { (body: Media[F]) =>
      EitherT {
        Concurrent[F].map(EntityDecoder.decodeText(body)) { (text: String) =>
          ReadResult
            .sequence(text.readCsv[List, List[Int]](rfc))
            .bimap(
              transformError,
              identity
            )
        }
      }
    }
}
