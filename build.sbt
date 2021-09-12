name := "sklep"

version := "1.0"

lazy val `sklep` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Atlassian's Maven Public Repository".at("https://packages.atlassian.com/maven-public/")
resolvers += "Akka Snapshot Repository".at("https://repo.akka.io/snapshots/")

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(evolutions, ehcache, ws, specs2 % Test, guice)
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"            // orm
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0" // migracje bazy danych
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.36.0.2"
libraryDependencies += "io.github.nafg.slick-migration-api" %% "slick-migration-api" % "0.8.2"
libraryDependencies += "com.mohiva" %% "play-silhouette" % "7.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-password-bcrypt" % "7.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-persistence" % "7.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-crypto-jca" % "7.0.0"
libraryDependencies += "com.mohiva" %% "play-silhouette-totp" % "7.0.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "5.0.1"
libraryDependencies += "com.iheart" %% "ficus" % "1.5.0"

dockerExposedPorts ++= Seq(9000, 3000)
