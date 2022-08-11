name := "TangNano4K"
version := "1.0"
scalaVersion := "2.11.12"

//val spinalVersion = "1.4.2"
//val spinalVersion = "1.5.0"
//val spinalVersion = "1.6.4"
//val spinalVersion = "1.7.2"
val spinalVersion =  "latest.release"
libraryDependencies ++= Seq(
  "com.github.spinalhdl" % "spinalhdl-core_2.11" % spinalVersion,
  "com.github.spinalhdl" % "spinalhdl-lib_2.11" % spinalVersion,
  compilerPlugin("com.github.spinalhdl" % "spinalhdl-idsl-plugin_2.11" % spinalVersion)
)

fork := true
//EclipseKeys.withSource := true
