package com.benburton.wistia

import java.io.File

import org.specs2.mutable.Specification

class WistiaTest extends Specification {

  val apiKey = "this is a fake api key"
  val wistia = new Wistia(apiKey)

  "upload" should {

    val file = new File("/path/to/file")

    "not yet be implemented" in {
      wistia.upload(file) must be equalTo(WistiaResponse.NotYetImplemented)
    }

  }

}
