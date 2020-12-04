package furhatos.app.captialguide

import furhatos.nlu.DynamicIntent
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.SimpleIntent
import furhatos.nlu.wikidata.City
import furhatos.skills.UserManager.random
import furhatos.util.Language


open class CityWithBooking(val city: City) {


    companion object {
        fun newInstance(city: City): CityWithBooking = CityWithBooking(city = city)
    }

//    open class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
//    }
//
//    open class BookActivity(var activity: Activity? = null) : Intent() {
//        override fun getExamples(lang: Language): List<String> {
//            return listOf("@activity", "I want @activity", "I would like @activity", "I want to book @activity")
//        }
//    }

    open val activityIntents = emptyList<SimpleIntent>()

    open fun toName(): String? {
        return ""
    }

    open fun tellHistory(): String {
        return ""
    }

    open fun getActivities(): List<String> {
        return emptyList()
    }
//        fun activities() : String {}
//        val activities : String? = null
}

//val stockholm = CityWithBooking (history = "Stockholm is very old.", activities = null)

class Stockholm(city: City) : CityWithBooking(city) {
    //    val city = City("Stockholm")
    val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most handsome men in the world!!"
    )

    override val activityIntents = listOf(
            SimpleIntent("castle"),
            SimpleIntent("old town"),
            SimpleIntent("archipelago"),
            SimpleIntent("city hall"))

//    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
//        override fun getEnum(lang: Language): List<String> {
//            return listOf("castle", "old town", "archipelago", "city hall")
//        }
//    }

    val activities_ = listOf(
            "Visit the castle.",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town.")

    override fun tellHistory(): String {
        return facts.shuffled().takeLast(1)[0]
    }

    override fun getActivities(): List<String> {
        return activities_
    }

    override fun toName(): String? {
        return city.name
    }
}

class Oslo(city: City) : CityWithBooking(city) {
    //    val city = City("Stockholm")
    val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most handsome men in the world!!"
    )

    override val activityIntents = listOf(
            SimpleIntent("castle"),
            SimpleIntent("old town"),
            SimpleIntent("archipelago"),
            SimpleIntent("city hall"))

//    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
//        override fun getEnum(lang: Language): List<String> {
//            return listOf("castle", "old town", "archipelago", "city hall")
//        }
//    }

    val activities_ = listOf(
            "Visit the castle.",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town.")

    override fun tellHistory(): String {
        return facts.shuffled().takeLast(1)[0]
    }

    override fun getActivities(): List<String> {
        return activities_
    }

    override fun toName(): String? {
        return city.name
    }
}
