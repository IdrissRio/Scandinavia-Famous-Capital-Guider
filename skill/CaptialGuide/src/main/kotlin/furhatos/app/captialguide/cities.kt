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

    fun toName(): String {
        return city.name!!
    }
}

class Stockholm(city: City) : CityWithBooking(city) {
    override val facts = listOf("Stockholm was founded in twelve fifty two (1252)",
            "The population is ${city.population}",
            "It has the most awesome group in the world!!",
            "It became the venue for the award of the first Nobel Prizes, in the year 1901.",
            "It’s subway is also known as the world’s longest art gallery, with the majority of its stations being adorned with paintings, sculptures and mosaics.",
            "It is sometimes referred to as ‘Venice of the North’, thanks to its beautiful buildings and exquisite architecture, abundant open water and numerous parks."
    )

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("Vasa Museum","castle", "old town", "archipelago", "city hall")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
            "Visit the Vasa Museum",
            "Visit the castle",
            "Go to the archipelago",
            "Go to the city hall",
            "Visit the Old Town")
}

class Oslo(city: City) : CityWithBooking(city) {
    override val facts = listOf("Oslo was founded around ten fourty nine (1049)",
            "The population is ${city.population}",
            "Oslo residents are Norway’s healthiest!!",
            "It used to be called Christiania",
            "The Nobel Peace Prize is awarded in Oslo",
            "Despite the fact that the nearest wild tigers are thousands of miles away, Oslo is known as Tigerstaden, or the Tiger City."
    )

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("National Gallery", "royal palace", "Ski Museum", "city hall")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
            "Visit the National Gallery",
            "Go to the royal palace",
            "Go to the the Holmenkollen Ski Museum",
            "Visit the city hall")
}

class Copenhagen(city: City) : CityWithBooking(city) {
    override val facts = listOf("Copenhagen was founded in eleven sixty seven (1167)",
        "The population is ${city.population}",
        "55% of Copenhageners commute to work by bicycle.",
        "COPENHAGEN HAS THE LONGEST PEDESTRIAN SHOPPING STREET IN THE WORLD",
        "COPENHAGEN BOASTS FIFTEEN (!) MICHELIN STARS, time to eat!",
        "COPENHAGEN HAS A SELF-GOVERNING FREETOWN:Christiana"
    )

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("Tivoli Garden", "Christiansborg Palace", "Nyhavn Harbor", "little Mermaid")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
        "Visit the Tivoli Garden",
        "Go to the Christiansborg Palace",
        "Go to the Nyhavn Harbor",
        "Visit the little Mermaid")
}

class Helsinki(city: City) : CityWithBooking(city) {
    override val facts = listOf("Helsinki was founded in fifteen fifty (1550)",
        "The population is ${city.population}",
        " In 2012 a special dog pool opened its doors in the east of Helsinki.",
        "Helsinki is the world’s coldest capital, with a yearly average temperature not exceeding 0 °C, and 51 days per year no sun can be seen in the sky.",
        "JINGLE BELLS, JINGLE BELLS, JINGLE ALL THE WAY! Santa Claus was born in Finland!!"
    )

    class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String>{
            return listOf("Kiasma museum", "amusement park", "churchs", "market")
        }
    }

    override fun getIntents() : IntentCandidate {
        return Activity()
    }

    override val activities = listOf(
        "Visit the Kiasma Museum",
        "Go to the Linnanmaki Amusement Park",
        "Go to the three famous churchs:Uspenski Cathedral,Helsinki Cathedral, and Temppeliaukio Kirkko",
        "Visit the Kauppatori Market")
}
