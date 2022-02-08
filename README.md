# challenge

Minimalistic solution for an offered coding challenge. A lot of shortcuts were taken, deliberately.

## Commands

`sbt run` in a root directory launches web service, which binds to `localhost:8080`.

`sbt test` runs tests.

`sbt assembly` creates fat jar that could be launched via `java -jar <path to jar>`.

## Notes

It's not stated what format should response have, so defaults are chosen (ie JSON of a required format for a specified error case, otherwise defaults for a `http4s` are used).

Functional tests are chosen over unit tests for a part of solution, cause I'm too lazy.

Logging is configured only for a console, with a minimal efforts.

Configuration is omitted deliberately.
