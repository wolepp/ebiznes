name := "sklep"

version := "1.0"

lazy val `sklep` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository".at("https://repo.akka.io/snapshots/")

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(evolutions, ehcache, ws, specs2 % Test, guice)
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"            // orm
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0" // migracje bazy danych
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.34.0"
libraryDependencies += "io.github.nafg.slick-migration-api" %% "slick-migration-api" % "0.8.2"
