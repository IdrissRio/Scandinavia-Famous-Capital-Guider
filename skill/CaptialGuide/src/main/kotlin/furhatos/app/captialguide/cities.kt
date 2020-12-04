package furhatos.app.captialguide

import furhatos.nlu.wikidata.City
import furhatos.skills.UserManager.random

open class CityWithBooking(val city: City) {


    companion object {
        fun newInstance(city: City): CityWithBooking = CityWithBooking(city = city)
    }

    open fun toName(): String? {
        return ""
    }

    open fun tellHistory(): String {
        return ""
    }

    open fun activities() {}
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

    override fun tellHistory(): String {
        return facts.shuffled().takeLast(1)[0]
    }

    override fun toName(): String? {
        return city.name
    }
}
