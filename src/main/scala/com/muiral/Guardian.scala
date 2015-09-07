package com.muiral

import spray.json.{JsValue, DefaultJsonProtocol}


/**
 * Created by alvaro on 9/6/15.
 */

case class KitchenSink(characterBase: JsValue, levelProgression: JsValue, emblemPath: String, backgroundPath: String,
                       emblemHash: Int, characterLevel: Int, baseCharacterLevel: Int, isPrestigeLevel: Boolean,
                       percentToNextLevel: Int) {
}


case class Details(membershipId: String, membershipType: Int, characterId: String, dateLastPlayed: String,
                    minutesPlayedThisSession: String, minutesPlayedTotal: String, powerLevel: Int, raceHash: Int,
                    genderHash: Int, classHash: Int, currentActivityHash: Int, lastCompletedStoryHash: Int, stats: JsValue,
                    customization: JsValue, grimoireScore: Int, peerView: JsValue, genderType: Int, classType: Int,
                    buildStatGroupHash: Int)

case class Progression(dailyProgress: Int, weeklyProgress: Int, currentProgress: Int, level: Int,
                                     step: Int, progressToNextLevel: Int, nextLevelAt: Int, progressionHash: Int)


case class Guardian(characterJson: JsValue) {



  object GuardianProtocol extends DefaultJsonProtocol {

    implicit val kitchenSinkFormat = jsonFormat9(KitchenSink)
    implicit val activityFormat = jsonFormat19(Details)
    implicit val progressionFormat = jsonFormat8(Progression)

  }

  import GuardianProtocol._

  lazy val kitchenSink = characterJson.convertTo[KitchenSink]
  lazy val details = kitchenSink.characterBase.convertTo[Details]
  lazy val progression = kitchenSink.levelProgression.convertTo[Progression]

  lazy val profile: Map[String, Any] = Map("level" -> kitchenSink.characterLevel.toString,
    "raceHash" -> details.raceHash, "genderHash" -> details.genderHash, "classHash" -> details.classHash)

}