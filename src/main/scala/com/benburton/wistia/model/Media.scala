package com.benburton.wistia.model

import play.api.libs.json._

/**
 * Class representing the media objects of the Wistia API: http://wistia.com/doc/data-api#medias
 */
case class Media(id: Long, name: String, mediaType: Option[String], created: String, updated: String, duration: Float,
                 hashedId: Option[String], progress: Float, status: Option[String], thumbnail: Thumbnail,
                 project: Option[Project] = None, assets: Seq[Asset] = Seq.empty[Asset])

object Media {

  implicit val ThumbnailReads = Thumbnail.Reads
  implicit val ProjectReads = Project.Reads
  implicit val AssetReads = Asset.Reads

  val Reads: Reads[Media] = new Reads[Media] {
    override def reads(json: JsValue): JsResult[Media] = JsSuccess(Media(
      id = (json \ "id").as[Long],
      name = (json \ "name").as[String],
      mediaType = (json \ "type").asOpt[String],
      created = (json \ "created").as[String],
      updated = (json \ "updated").as[String],
      duration = (json \ "duration").as[Float],
      hashedId = (json \ "hashed_id").asOpt[String],
      progress = (json \ "progress").as[Float],
      status = (json \ "status").asOpt[String],
      thumbnail = Json.fromJson[Thumbnail]((json \ "thumbnail").as[JsObject])
        .getOrElse(throw parseException("thumbnail")),
      project = (json \ "project").asOpt[JsObject].map(Json.fromJson[Project](_)
        .getOrElse(throw parseException("project"))),
      assets = (json \ "assets").asOpt[Seq[JsObject]]
        .getOrElse(Seq.empty[JsObject]).map(Json.fromJson[Asset](_).getOrElse(throw parseException("assets")))
    ))
  }

  private def parseException(key: String) = new IllegalStateException(s"Could not parse JSON response for $key")

}