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
class ChooseCity(var city: City? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "@city",
                "I want to go to @city",
                "I would like to go to @city",
                "I would like to visit @city",
                "Take me to @city",
                "Please, @city")
    }
}

class ChangeCity : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Change city",
                "I want to go somewhere else",
                "I want to go to a different city",
                "I would like to visit a different city",
                "Please, switch city"
        )
    }
}

class GetInformation() : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I would like some information",
                "Information",
                "Some information",
                "Tell me something",
                "Provide some information",
                "I want some information"
        )
    }
}

class BookActivity(val activity: Activity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I want to book an activity",
                "I would like to book an activity",
                "An activity would be great",
                "Book activity",
                "Booking",
                "What activities are there?",
                "Tell me about the activities",
                "I want to book @activity",
                "I want to @activity",
                "I would like to @activity",
                "I want to go to @activity",
                "I would like to book an activity",
                "I want to visit @activity",
                "I would like to visit @activity"
        )
    }
}

class ListActivites() : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(

                "What were my options?",
                "Which activities were there?",
                "Tell me about the activities",
                "Which activities are there?"
        )
    }
}

class ListBookedActivities : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "List current bookings",
                "List current activities",
                "What have I booked?",
                "Which activities have I booked?",
                "What are my current bookings?"
        )
    }
}

class Activity : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return available_activities
    }
}

class BookActivityInCity(
        val activity: Activity? = null,
        val city: City? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I want to book @activity in @city",
                "I want to @activity in @city",
                "In @city I would like to @activity",
                "I want to go to @activity in @city",
                "I would like to book an activity in @city",
                "I want to visit @activity in @city",
                "I would like to visit @activity in @city"
        )
    }
}

//class ActivityList : ListEntity<String>()
//class InformationOrBooking()