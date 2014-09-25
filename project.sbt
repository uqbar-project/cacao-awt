name := "cacao-awt"

description := "AWT-based Cacao implementation"

scalaVersion := "2.11.1"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val cacaoAWT = FDProject(
	"org.uqbar" %% "cacao" % "[0.0.1-SNAPSHOT)",
    "org.scalatest" %% "scalatest" % "[2.2,)" % "test"
)

///////////////////////////////////////////////////////////////////////////////////////////////////

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)

scalacOptions += "-feature"