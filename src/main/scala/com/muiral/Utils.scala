package com.muiral

/**
 * Created by alvaro on 9/6/15.
 */


import org.apache.http.client.fluent.Request
import spray.json.{JsonParser, JsValue}
import spray.json.DefaultJsonProtocol._


class Utils {
  val endpoint = "http://www.bungie.net/Platform/Destiny"

  def parse(x: String): JsValue = {
    JsonParser(x)
  }

  def getRestContent(operation: String): Any = {
    val url       = endpoint + operation
    Request.Get(url).execute().returnContent()
  }

  def parseRequest(operation: String): JsValue = {
    val request = getRestContent(operation)
    parse(request.toString)
  }

  def getDestinyMembershipId(membershipType: Int, displayName: String): JsValue = {
    val operation = s"/$membershipType/Stats/GetMembershipIdByDisplayName/$displayName/"
    parseRequest(operation)
  }

  def getAccountInfo(membershipType: Int, destinyMembershipId: String): JsValue = {
    val operation = s"/$membershipType/Account/$destinyMembershipId/"
    parseRequest(operation)
  }

}