scalaVersion := "2.10.0-RC5"

organization := "org.gtri.util"

name := "xsdbuilder.impl"

version := "1.0-SNAPSHOT"

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

resolvers += "iead-all" at "https://iead.ittl.gtri.org/artifactory/all" // need this for getting IEAD artifacts

//libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1"

libraryDependencies += "com.google.guava" % "guava" % "11.0.2"

libraryDependencies += "org.gtri.util" % "xsddatatypes" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" % "iteratee.api" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" %% "iteratee.impl" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" %% "iteratee.main" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" % "xmlbuilder.api" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" %% "xmlbuilder.impl" % "1.0-SNAPSHOT"

libraryDependencies += "org.gtri.util" % "xsdbuilder.api" % "1.0-SNAPSHOT"

publishTo <<= {    // set publish repository url according to whether `version` ends in "-SNAPSHOT"
  val releases = "iead-artifactory" at "https://iead.ittl.gtri.org/artifactory/internal"
  val snapshots = "iead-artifactory-snapshots" at "https://iead.ittl.gtri.org/artifactory/internal-snapshots"
  version { v =>
    if (v.endsWith("-SNAPSHOT"))
      Some(snapshots)
    else Some(releases)
  }
}