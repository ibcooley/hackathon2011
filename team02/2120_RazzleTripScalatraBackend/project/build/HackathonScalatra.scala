// save as project/build/HelloScalatraBuild.scala
import sbt._
class HelloScalatraBuild(info: ProjectInfo) extends DefaultWebProject(info)
{
  val scalatraVersion = "2.0.0.M2"
  val scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"
  val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.22" % "test"

  // Declare MySQL connector Dependency
  val mysql = "mysql" % "mysql-connector-java" % "5.1.12"

  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
}
