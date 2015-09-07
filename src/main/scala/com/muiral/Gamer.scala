package com.muiral

import spray.json.{JsValue, DefaultJsonProtocol}


/**
 * Created by alvaro on 9/6/15.
 */

case class RootFormat(ErrorCode: Int, Message: String, ErrorStatus: String, Response: JsValue,
                           ThrottleSeconds: Int, MessageData: JsValue)

case class DataFormat(data: Map[String, JsValue])

case class InventoryFormat(buckets: JsValue, currencies: List[JsValue])



case class Gamer(system: String, displayName: String) {
  private val utils = new com.muiral.Utils

  val membershipType = system match {
    case s if s.toLowerCase.startsWith("x") => 1
    case s if s.toLowerCase.startsWith("p") => 2
  }

  object AccountProtocol extends DefaultJsonProtocol {
    implicit val baseFormat = jsonFormat6(RootFormat)
    implicit val dataFormat = jsonFormat1(DataFormat)
    implicit val inventoryFormat = jsonFormat2(InventoryFormat)
  }

  import AccountProtocol._

  private val membershipJson = utils.getDestinyMembershipId(membershipType, displayName)
  lazy val destinyMembershipId = membershipJson.convertTo[RootFormat].Response.toString().replace('"', ' ').trim
  private val accountInfoJson = utils.getAccountInfo(membershipType, destinyMembershipId)
  private val accountDetails = accountInfoJson.convertTo[RootFormat].Response.convertTo[DataFormat].data
  lazy val characters = accountDetails("characters").convertTo[List[JsValue]]
  lazy val inventory = accountDetails("inventory").convertTo[InventoryFormat]
  lazy val grimoireScore = accountDetails("grimoireScore")

  def getGuardian(charNum: Int) = {
    Guardian(characters(charNum))
  }


}