package com.benburton.wistia

import com.ning.http.multipart.{StringPart, Part}

case class UploadOptions(projectId: Option[String] = None, name: Option[String] = None, description: Option[String] = None) {

  def toParts: Array[Part] =
    Seq(projectId.map(new StringPart("project_id", _)), name.map(new StringPart("name", _)),
        description.map(new StringPart("description", _))).flatten.toArray

}

object UploadOptions {
  val Default = UploadOptions(projectId = None, name = None, description = None)
}