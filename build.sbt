val supportSdkVersion = "23.1.1"
val doGeneration = taskKey[Seq[File]]("android-conversions-generator")

TaskKey[Unit]("publishSigned") := {}

publishLocal := {}

sonatypeProfileName := "com.hanhuy"

crossScalaVersions in Global += "2.11.7"

val settings = android.Plugin.androidBuildJar ++ Seq(
  lintEnabled := false,
  scalacOptions in Compile ++= "-feature" :: "-deprecation" :: Nil,
  autoScalaLibrary := true,
  platformTarget := "android-23",
  sourceGenerators in Compile <+= doGeneration,
  crossPaths := true,
  organization := "com.hanhuy.android",
  version := supportSdkVersion,
  javacOptions ++= "-target" :: "1.7" :: "-source" :: "1.7" :: Nil,
  // sonatype publishing options follow
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomIncludeRepository := { _ => false },
  pomExtra := {
    <scm>
      <url>git@github.com:pfn/android-conversions.git</url>
      <connection>scm:git:git@github.com:pfn/android-conversions.git</connection>
    </scm>
      <developers>
        <developer>
          <id>pfnguyen</id>
          <name>Perry Nguyen</name>
          <url>https://github.com/pfn</url>
        </developer>
      </developers>
  },
  licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php")),
  homepage := Some(url("https://github.com/pfn/android-conversions"))
)

val framework = project.settings(settings).settings(name := "scala-conversions")

val supportv4 = project.in(file("support-v4")).settings(settings).settings(name := "scala-conversions-v4").dependsOn(framework)

val appcompatv7 = project.in(file("appcompat-v7")).settings(settings).settings(name := "scala-conversions-appcompat").dependsOn(supportv4)

val design = project.settings(settings).settings(name := "scala-conversions-design").dependsOn(appcompatv7)

doGeneration in framework := {
  val bcp = (bootClasspath in framework).value
  ConversionsGenerator((sourceManaged in (framework,Compile)).value, bcp, bcp.head.data, "com.hanhuy.android")
}

doGeneration in supportv4 := {
  val fcp = (dependencyClasspath in (supportv4,Compile)).value
  val bcp = (bootClasspath in supportv4).value
  val jar = fcp map (_.data) find (_.getName contains "support-v4-2")
  ConversionsGenerator(
    (sourceManaged in (supportv4,Compile)).value,
    bcp ++ fcp, jar.get, "com.hanhuy.android.v4",
    bcp.head.data :: Nil,
    "com.hanhuy.android" :: Nil)
}

doGeneration in appcompatv7 := {
  val bcp = (bootClasspath in appcompatv7).value
  val fcp = (dependencyClasspath in (appcompatv7,Compile)).value
  val jar = fcp map (_.data) find (_.getName contains "appcompat-v7")
  ConversionsGenerator(
    (sourceManaged in (appcompatv7,Compile)).value,
    bcp ++ fcp, jar.get, "com.hanhuy.android.appcompat",
    bcp.head.data :: Nil,
    "com.hanhuy.android" :: Nil)
}

doGeneration in design := {
  val bcp = (bootClasspath in design).value
  val fcp = (dependencyClasspath in (design,Compile)).value
  val jar = fcp map (_.data) find (_.getName contains "design")
  ConversionsGenerator(
    (sourceManaged in (design,Compile)).value,
    bcp ++ fcp, jar.get, "com.hanhuy.android.design",
    bcp.head.data :: Nil,
    "com.hanhuy.android" :: Nil)
}

libraryDependencies in design += "com.android.support" % "design" % supportSdkVersion % "provided"

libraryDependencies in appcompatv7 += "com.android.support" % "appcompat-v7" % supportSdkVersion % "provided"

libraryDependencies in supportv4 += "com.android.support" % "support-v4" % supportSdkVersion % "provided"
