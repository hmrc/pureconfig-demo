import play.core.PlayVersion.current
import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.SbtArtifactory
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "pureconfig-demo"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory)
  .settings(
    majorVersion := 0
  )
  .settings(publishingSettings: _*)
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(resolvers += Resolver.jcenterRepo)

libraryDependencies ++= Seq(
  "uk.gov.hmrc" %% "govuk-template" % "5.23.0",
  "uk.gov.hmrc" %% "play-ui" % "7.22.0",
  "uk.gov.hmrc" %% "bootstrap-play-25" % "3.11.0",
  "com.github.pureconfig" %% "pureconfig" % "0.9.2",

  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.jsoup" % "jsoup" % "1.10.2" % "test",
  "com.typesafe.play" %% "play-test" % current % "test",
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  "uk.gov.hmrc" %% "service-integration-test" % "0.2.0" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "test"
)
