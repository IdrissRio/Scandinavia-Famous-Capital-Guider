package furhatos.app.captialguide.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.wikidata.City
import furhatos.util.Language


class CityName : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Stockholm", "Oslo", "Helsinki", "Copenhagen")
    }
}

// Our ChooseCity intent
class ChooseCity(var city : City? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@city", "I want to go to @city", "Please, @city")
    }
}

class GetInformation() : Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf("I would like some information", "Information")
    }
}

class BookActivity() : Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf("An activity would be great", "Book activity", "booking")
    }
}

//class InformationOrBooking()