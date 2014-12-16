package com.benburton.wistia

case class WistiaResponse(status: Int, message: String)

object WistiaResponse {
  val NotYetImplemented = WistiaResponse(500, "Not yet implemented")
}