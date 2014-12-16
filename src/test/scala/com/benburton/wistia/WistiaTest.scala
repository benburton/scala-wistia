package com.benburton.wistia

import java.io.File

import org.specs2.mutable.Specification

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class WistiaTest extends Specification {

  import scala.concurrent.ExecutionContext.Implicits.global

  val apiKey = "this is a fake api key"
  val wistia = new Wistia(apiKey)

  "upload" should {

    val file = new File("/path/to/file")

    "not yet be implemented" in {
      Await.result(wistia.upload(file), Duration.Inf) must be equalTo(WistiaResponse.NotYetImplemented)
    }

  }

}
