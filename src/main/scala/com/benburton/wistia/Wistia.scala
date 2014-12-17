package com.benburton.wistia

import java.io.{ByteArrayOutputStream, File}

import com.benburton.wistia.model.{Project, Media}
import com.ning.http.client.{FluentCaseInsensitiveStringsMap, AsyncHttpClientConfig}
import com.ning.http.multipart._
import org.apache.commons.codec.binary.Base64
import play.api.http.{ContentTypeOf, Writeable}
import play.api.libs.json.Json
import play.api.libs.ws.{Response, WS}

import scala.concurrent.Future

/**
 * Class to use for interfacing with the Wistia API.
 */
class Wistia(apiKey: String) {

  import Wistia._
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val MediaReads = Media.Reads
  implicit val ProjectReads = Project.Reads

  val timeout = 60000 * 10

  val authPart = new StringPart("api_password", apiKey)
  val authHeader = "Authorization" -> ("Basic " + new String(Base64.encodeBase64(s"api:$apiKey".getBytes)))

  def upload(file: File, options: UploadOptions = UploadOptions.Default): Future[WistiaResponse[Media]] = {
    val mpre = new MultipartRequestEntity(
      options.toParts :+ authPart :+ new FilePart("file", file), new FluentCaseInsensitiveStringsMap)
    val baos = new ByteArrayOutputStream
    mpre.writeRequest(baos)
    val bytes = baos.toByteArray
    val contentType = mpre.getContentType

    WS.url(Urls.Upload).post(bytes)(Writeable.wBytes, ContentTypeOf(Some(contentType)))
  }.map(response => WistiaResponse(response.status, obj =
    Some(Json.fromJson(Json.parse(response.body))(Media.Reads).getOrElse(throw new Exception("What?!")))))

  def get(hashedId: String): Future[WistiaResponse[Media]] =
    WS.url(Urls.Media(hashedId)).withHeaders(authHeader).get
      .map(response => WistiaResponse(response.status, obj = Some(Json.fromJson[Media](Json.parse(response.body))
      .getOrElse(throw parseException("media")))))

  def get(): Future[WistiaResponse[Seq[Media]]] =
    WS.url(Urls.Medias).withHeaders(authHeader).get.map(response => WistiaResponse(response.status, obj =
      Some(Json.fromJson[Seq[Media]](Json.parse(response.body)).getOrElse(throw parseException("media")))))

  def getProject(hashedId: String): Future[WistiaResponse[Project]] =
    WS.url(Urls.Project(hashedId)).withHeaders(authHeader).get
      .map(response => { println(Json.prettyPrint(Json.parse(response.body))); WistiaResponse(response.status, obj =
      Some(Json.fromJson[Project](Json.parse(response.body)).getOrElse(throw parseException("project"))))})

  private def parseException(key: String) = new IllegalStateException(s"Could not parse JSON response for $key")

}

object Wistia {
  object Urls {
    val Upload = "https://upload.wistia.com/"
    def Media(hashedId: String) = s"https://api.wistia.com/v1/medias/$hashedId.json"
    val Medias = "https://api.wistia.com/v1/medias.json"
    def Project(hashedId: String) = s"https://api.wistia.com/v1/projects/$hashedId.json"
  }
}
