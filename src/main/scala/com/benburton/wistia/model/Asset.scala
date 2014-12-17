package com.benburton.wistia.model

import play.api.libs.json.{JsSuccess, JsResult, JsValue, Reads}

case class Asset(url: String, width: Int, height: Int, fileSize: Long, contentType: String, assetType: String)

object Asset {

  val Reads = new Reads[Asset] {
    override def reads(json: JsValue) = JsSuccess(Asset(
      url = (json \ "url").as[String],
      width = (json \ "width").as[Int],
      height = (json \ "height").as[Int],
      fileSize = (json \ "fileSize").as[Long],
      contentType = (json \ "contentType").as[String],
      assetType = (json \ "type").as[String]
    ))
  }

}
