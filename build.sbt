import android.Keys._

android.Plugin.androidBuild

crossPaths := true

crossScalaVersions += "2.11.2"

scalacOptions in Compile ++= "-feature" :: "-deprecation" :: Nil

javacOptions in Global ++= "-target" :: "1.7" :: "-source" :: "1.7" :: Nil

manifest in Android := <manifest package="com.hanhuy.android.conversions">
  <application/>
</manifest>

processManifest in Android := file("/")

buildConfigGenerator in Android := Nil

rGenerator in Android := Nil

name := "scala-conversions"

organization := "com.hanhuy.android"

version := "1.1"

platformTarget in Android := "android-22"

debugIncludesTests in Android := false

publishArtifact in (Compile,packageBin) := true

publishArtifact in (Compile,packageSrc) := true

// sonatype publishing options follow
publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

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
}

licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("https://github.com/pfn/android-conversions"))
