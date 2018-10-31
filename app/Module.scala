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

import com.google.inject.{AbstractModule, Provides}
import config.{AppConfig, GoogleAnalytics}
import javax.inject.{Named, Singleton}
import eu.timepit.refined.pureconfig._

class Module extends AbstractModule {

  val cfg = pureconfig.loadConfigOrThrow[AppConfig]

  override def configure(): Unit = {}

  @Provides @Singleton
  def appConfig: AppConfig = cfg

  @Provides @Singleton
  def googleAnalytics: GoogleAnalytics = cfg.googleAnalytics

  @Provides @Singleton @Named("someArray")
  def someArray: Seq[Int] = cfg.microservice.services.pureconfigDemo.someArray

}
