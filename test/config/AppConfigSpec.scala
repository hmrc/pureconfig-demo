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

import com.typesafe.config.ConfigFactory
import org.scalatest.{MustMatchers, WordSpec}
import eu.timepit.refined.pureconfig._
import pureconfig.error.ConfigReaderException

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

    "parse sequence of ints" in {
      cfg.microservice.services.pureconfigDemo.someArray must be(Seq(42, 24))
    }

    "fail refined validation given negative int" in {
      val config = ConfigFactory.parseString(
        """
          |appName = foo
          |microservice {
          | services {
          |   pureconfig-demo {
          |     positive-int = -1
          |   }
          |  }
          |}
        """.stripMargin)
      val ex = intercept[ConfigReaderException[_]] {
        pureconfig.loadConfigOrThrow[AppConfig](config)
      }
      ex.failures.toList.size must be(1)
      ex.failures.toList.head.description must include("Cannot convert '-1'")
    }
  }

}
