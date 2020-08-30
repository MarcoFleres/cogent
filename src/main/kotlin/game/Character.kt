package game

import game.Skill.*
import kotlin.random.Random

enum class Skill {
    Strength,
    Reflex,
    Intelligence
}

enum class Attribute(vararg val skills : Skill){
    Perception(Strength, Reflex, Intelligence),
    Athletics(Strength),
    Grapple(Strength),
    Swim(Strength),
    Aim_Throw(Strength),
    Acrobatics(Reflex),
    Ride_Pilot(Reflex),
    Sleight_Of_Hand(Reflex),
    Stealth(Reflex),
    Deception(Intelligence),
    Persuation(Intelligence),
    Infiltration(Intelligence),
    Survival(Intelligence)
}

data class Character(
    var name : String,
    var level: Int = 1,
    val skills : Map<Skill, Int> = emptyMap(),
    val attributes : Map<Attribute, Int> = emptyMap()
) {
    companion object {
        fun randomCharacter() : Character {
            val skills = mutableMapOf<Skill, Int>()
            val attributes = mutableMapOf<Attribute, Int>()

            (1..2).forEach {
                values().asList().random().let { pick ->
                    skills.put(pick, skills.getOrElse(pick, {0})+1)
                }
            }

            (1..8).forEach {
                Attribute.values().asList().random().let { pick ->
                    attributes.put(pick, attributes.getOrElse(pick, {0})+1)
                }
            }

            return Character(
                name = Random.nextBits(8).toString(),
                skills = skills,
                attributes = attributes
            )
        }
    }

    fun diceAmount(attribute : Attribute) : Int {
        return 3 +
                attributes.getOrElse(attribute, {0}) +
                attribute.skills.sumBy { skills.getOrElse(it, {0}) }
    }
}
