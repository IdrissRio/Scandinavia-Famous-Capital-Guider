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
    open val activities = emptyList<String>()
    open val facts = emptyList<String>()

    fun toName(): String? {
        return city.name
    }
}

class Stockholm(city: City) : CityWithBooking(city) {
    override val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most handsome men in the world!!"
    )

    override val activityIntents = listOf(
            SimpleIntent("castle"),
            SimpleIntent("old town"),
            SimpleIntent("archipelago"),
            SimpleIntent("city hall"))

    override val activities = listOf(
            "Visit the castle.",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town.")
}

class Oslo(city: City) : CityWithBooking(city) {
    override val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most handsome men in the world!!"
    )

    override val activityIntents = listOf(
            SimpleIntent("castle"),
            SimpleIntent("old town"),
            SimpleIntent("archipelago"),
            SimpleIntent("city hall"))


    override val activities = listOf(
            "Visit the castle.",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town.")
}
