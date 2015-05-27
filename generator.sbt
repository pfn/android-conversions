import sbt._
import android.Keys._

import collection.JavaConverters._

val doGeneration = taskKey[Seq[File]]("android-conversions-generator")

sourceGenerators in Compile <+= doGeneration

doGeneration := ConversionsGenerator(
    (sourceManaged in Compile).value, file((platformJars in Android).value._1))
