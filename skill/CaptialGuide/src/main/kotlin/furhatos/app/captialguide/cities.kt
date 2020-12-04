package furhatos.app.captialguide

import furhatos.nlu.*
import furhatos.nlu.wikidata.City
import furhatos.skills.UserManager.random
import furhatos.util.Language


open class CityWithBooking(val city: City) {
    companion object {
        fun newInstance(city: City): CityWithBooking = CityWithBooking(city = city)
    }

    open class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf()
        }
    }

    open fun getIntents() : IntentCandidate {
        return Activity()
    }

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

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("castle", "old town", "archipelago", "city hall")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
            "Visit the castle",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town")
}

class Oslo(city: City) : CityWithBooking(city) {
    override val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most handsome men in the world!!"
    )

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("castle", "old town", "archipelago", "city hall")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
            "Visit the castle",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town")
}
