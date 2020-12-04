package furhatos.app.captialguide.nlu

import furhatos.app.captialguide.available_activities
import furhatos.nlu.DynamicIntent
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
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

class ChangeCity : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Change city", "I want to go somewhere else", "Please, switch city")
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

class ListActivites() : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What were my options?", "Which activites were there?", "Tell me about the activities")
    }
}

class ListBookedActivities : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What have I booked?", "Which activities have I booked?")
    }
}

class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String>{
        return available_activities
    }
}

class BookActivityInCity(
        val activity : Activity? = null,
        val city: City? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want to book @activity in @city", "I want to @activity in @city", "In @city I would like to @activity",
        "I want to go to @activity in @city")
    }
}

//class ActivityList : ListEntity<String>()
//class InformationOrBooking()