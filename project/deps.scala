import sbt._

object V {
  val cats    = "2.7.0"
  val ce      = "3.3.5"
  val l4c     = "2.2.0"
  val slf4j   = "1.7.35"
  val logback = "1.2.10"
  val circe   = "0.14.1"
  val http4s  = "0.23.10"
  val kantan  = "0.6.2"
}

object TV {
  val mu      = "0.7.29"
  val muh     = "0.9.3"
}

object D {
  val cats = Seq(
    "org.typelevel" %% "cats-core"   % V.cats,
    "org.typelevel" %% "cats-effect" % V.ce
  )

  val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % V.circe)

  val kantan = Seq(
    "com.nrinaudo" %% "kantan.csv",
    "com.nrinaudo" %% "kantan.csv-cats"
  ).map(_ % V.kantan)

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl",
    "org.http4s" %% "http4s-circe",
    "org.http4s" %% "http4s-blaze-server"
  ).map(_ % V.http4s)

  val logging = Seq(
    "org.typelevel" %% "log4cats-slf4j"  % V.l4c,
    "org.slf4j"      % "slf4j-api"       % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback
  )

  val tests = Seq(
    "com.alejandrohdezma" %% "http4s-munit" % TV.muh,
    "org.scalameta"       %% "munit"        % TV.mu
  ).map(_ % Test)
}
