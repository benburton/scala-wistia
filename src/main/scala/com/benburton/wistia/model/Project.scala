package com.benburton.wistia.model

import play.api.libs.json._

case class Project(id: Long, name: String, mediaCount: Option[Int], created: Option[String], updated: Option[String],
                   hashedId: String, anonymousCanUpload: Option[Boolean], anonymousCanDownload: Option[Boolean],
                   public: Option[Boolean], publicId: Option[String], description: Option[String], media: Seq[Media])

object Project {

  val Reads: Reads[Project] = new Reads[Project] {

    override def reads(json: JsValue) = JsSuccess(Project(
      id = (json \ "id").as[Long],
      name = (json \ "name").as[String],
      mediaCount = (json \ "mediaCount").asOpt[Int],
      created = (json \ "created").asOpt[String],
      updated = (json \ "updated").asOpt[String],
      hashedId = (json \ "hashed_id").asOpt[String].getOrElse((json \ "hashedId").as[String]),
      anonymousCanUpload = (json \ "anonymousCanUpload").asOpt[Boolean],
      anonymousCanDownload = (json \ "anonymousCanDownload").asOpt[Boolean],
      public = (json \ "public").asOpt[Boolean],
      publicId = (json \ "publicId").asOpt[String],
      description = (json \ "description").asOpt[String],
      media = (json \ "medias").asOpt[Seq[JsObject]].getOrElse(Seq.empty).map(Json.fromJson[Media](_)(Media.Reads)
        .getOrElse(throw parseException("medias")))
    ))
  }

  private def parseException(key: String) = new IllegalStateException(s"Could not parse JSON response for $key")

}
