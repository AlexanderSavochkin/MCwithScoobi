import AssemblyKeys._

assemblySettings


name := "MCwithScoobi"

version := "0.1"

scalaVersion := "2.9.2"

libraryDependencies += "com.nicta" %% "scoobi" % "0.6.1-cdh4"

scalacOptions ++= Seq("-Ydependent-method-types", "-deprecation")

resolvers ++= Seq(
    "nicta's avro" at "http://nicta.github.com/scoobi/releases",
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
