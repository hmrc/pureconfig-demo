/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Positive
import pureconfig.{CamelCase, ConfigFieldMapping, KebabCase, ProductHint}

case class AppConfig(appName: String,
                     contactFrontend: ContactFrontend = ContactFrontend(),
                     assets: Assets = Assets(),
                     googleAnalytics: GoogleAnalytics = GoogleAnalytics(),
                     microservice: Microservice = Microservice())

object AppConfig {
  implicit val appNameHint: ProductHint[AppConfig] = ProductHint(new ConfigFieldMapping {
    def apply(fieldName: String): String = fieldName match {
      case "appName" => fieldName
      case _ => KebabCase.fromTokens(CamelCase.toTokens(fieldName))
    }
  })
}

case class ContactFrontend(host: String = "http://localhost:9250", serviceIdentifier: String = "MyService") {
  lazy val reportAProblemPartialUrl = s"$host/contact/problem_reports_ajax?service=$serviceIdentifier"
  lazy val reportAProblemNonJSUrl = s"$host/contact/problem_reports_nonjs?service=$serviceIdentifier"
}

case class Assets(version: String = "2.149.0", url: String = "http://localhost:9032/assets/") {
  lazy val prefix: String = s"$url$version"
}

case class GoogleAnalytics(token: String = "N/A", host: String = "auto")

case class Microservice(services: Services = Services())

case class Services(pureconfigDemo: PureconfigDemo = PureconfigDemo())

// the whole "feature map" value will be overwritten if feature block defined in application.conf
case class PureconfigDemo(features: Map[String, String] = Map("quix" -> "enabled"), someArray: Seq[Int] = Seq.empty, positiveInt: Int Refined Positive = 24)
