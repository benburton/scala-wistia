## scala-wistia

scala-wistia is a Scala library for interacting with the [wisita](http://www.wistia.com/) API.

### Usage

#### Setup

Set up with SBT:

    // TODO

#### Upload

    val apiKey = "" // comes from your config
    val wistia = Wistia(apiKey)
    val response: Future[WisitaResponse] = wisita.upload(file)