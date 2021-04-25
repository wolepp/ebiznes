name := "sklep"

version := "1.0"

lazy val `sklep` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository".at("https://repo.akka.io/snapshots/")

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)

//unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
