package com.benburton.wistia

case class WistiaResponse[+A](status: Int, message: Option[String] = None, obj: Option[A] = None) {
  def get: A = obj.getOrElse(throw new IllegalStateException("WistiaResponse did not contain object"))
  def getOrElse[B >: A](default: => B): B = obj.getOrElse(default)
}

object WistiaResponse {
  val NotYetImplemented = WistiaResponse(500, Some("Not yet implemented"))
}
