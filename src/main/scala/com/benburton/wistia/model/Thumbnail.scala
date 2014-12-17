package com.benburton.wistia.model

import play.api.libs.json.{JsSuccess, JsResult, JsValue, Reads}

case class Thumbnail(url: String, height: Int, width: Int)

object Thumbnail {

  val Reads = new Reads[Thumbnail] {
    override def reads(json: JsValue): JsResult[Thumbnail] = JsSuccess(Thumbnail(
      url = (json \ "url").as[String],
      height = (json \ "height").as[Int],
      width = (json \ "width").as[Int]
    ))
  }

}