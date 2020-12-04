package furhatos.app.captialguide.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.captialguide.nlu.*
import furhatos.nlu.wikidata.City

val Start: State = state(Interaction) {

    onEntry {
        furhat.say("Hi there.")
        goto(CityOptions)
    }

//    onResponse<Yes> {
//        furhat.say("I like humans.")
//    }
//
//    onResponse<No> {
//        furhat.say("That's sad.")
//    }
}

val CityOptions = state {

    onEntry {
        furhat.ask("Which city?")

    }

    onResponse<ChooseCity> {
        furhat.say("You chose ${it.intent.city?.text}")
        val city = it.intent.city
        if (city != null) {
            goto(CityRecieved(city))
        }
        else {
            propagate()
        }
    }

}

val BookingOptions = state {

}

val Options = state {

//    onEntry {
//
//        furhat.ask("Would you like some information about ${users.current.information.city.text} or book an activity?")
//    }

    onResponse<BookActivity> {
        furhat.say("You would like to book an activity in ${users.current.information.city.text}")
    }

    onResponse<GetInformation> {
        furhat.say("You would like to get information about ${users.current.information.city.text}")
    }


}

val SuggestBookings = state {

}

val CityIntroduction = state {


}

fun CityRecieved(city: City): State = state(Options) {
    onEntry {
        users.current.information.city = city
        furhat.ask("Would you like some information about ${users.current.information.city.text} or book an activity?")
//        furhat.say("You want ${city.text}")
//        users.current.city = city
    }

}

//fun TakeBookingOrder(order: BookingOrder): State = state {
//
//}