import AssemblyKeys._

assemblySettings


name := "MCwithScoobi"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies += "com.nicta" %% "scoobi" % "0.7.0-RC1-cdh4"

scalacOptions ++= Seq("-deprecation")

resolvers ++= Seq(
    "cloudera" at "https://repository.cloudera.com/content/repositories/releases",
    "Sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case x => {
      val oldstrat = old(x)
      if (oldstrat == MergeStrategy.deduplicate) MergeStrategy.first
      else oldstrat
    }
  }
}
