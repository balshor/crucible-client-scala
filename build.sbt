organization := "com.bizo"

name := "crucible-client-scala"

version := "1.0.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:_")

libraryDependencies ++= Seq(
  "com.sun.jersey" % "jersey-bundle" % "1.9.1",
  "javax.ws.rs" % "jsr311-api" % "1.1.1",
  "org.json4s" %% "json4s-native" % "3.2.9",
  "commons-io" % "commons-io" % "2.4",
  "junit" % "junit" % "4.10" % "test",
  "com.novocode" % "junit-interface" % "0.10-M4" % "test"
)

EclipseKeys.withSource := true

pomExtra := {
 <url>https://github.com/ogrodnek/crucible-client-scala</url>
  <licenses>
    <license>
      <name>The Apache Software License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/ogrodnek/crucible-client-scala</url>
    <connection>https://github.com/ogrodnek/crucible-client-scala.git</connection>
  </scm>
  <developers>
    <developer>
      <id>larry</id>
      <name>Larry Ogrodnek</name>
      <email>larry@bizo.com</email>
    </developer>
  </developers>
}