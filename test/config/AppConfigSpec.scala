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

import org.scalatest.{MustMatchers, WordSpec}

class AppConfigSpec extends WordSpec with MustMatchers {

  val cfg = pureconfig.loadConfigOrThrow[AppConfig]

  "app config" should {
    "parse camelCase config property for appName" in {
      cfg.appName must be("pureconfig-demo")
    }

    "parse kebab-case config property for contact-frontend" in {
      cfg.contactFrontend.host must be("http://localhost:9250")
    }

    "parse map of features" in {
      cfg.microservice.services.pureconfigDemo.features("foo") must be("enabled")
    }
  }

}
