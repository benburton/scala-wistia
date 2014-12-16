package com.benburton.wistia

import java.io.File

import scala.concurrent._

class Wistia(apiKey: String) {

  import scala.concurrent.ExecutionContext.Implicits.global

  def upload(file: File): Future[WistiaResponse] = future {
    WistiaResponse.NotYetImplemented
  }

}
