package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.captialguide.nlu.*
import furhatos.nlu.wikidata.City
import furhatos.app.captialguide.Stockholm as Stockholm

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
        } else {
            propagate()
        }
    }

}

val BookingOptions = state {

}

val Options = state(Interaction) {

    onEntry {

        furhat.ask("Would you like some information about ${users.current.information.city.toName()} or book an activity?")
    }

    onReentry {
        furhat.ask("Would you like some more information about ${users.current.information.city.toName()} or book an activity?")
    }

    onResponse<BookActivity> {
        furhat.say("You would like to book an activity in ${users.current.information.city.toName()}")
    }

    onResponse<GetInformation> {
        goto(CityInformation)

    }


}

val SuggestBookings = state {

    onEntry {

    }

}

val CityInformation = state {

    onEntry {
        furhat.say("I will tell you a fact")
        val city = users.current.information.city
        furhat.say("You would like to get information about ${city.toName()}")
        furhat.say(city.tellHistory())
//        goto(Options)
    }


}

fun CityRecieved(city: City): State = state(Options) {
    onEntry {
        users.current.information.city = Class.forName("furhatos.app.captialguide." + city.name).kotlin.constructors.first().call(city) as CityWithBooking
        goto(Options)
//        furhat.say("You want ${city.text}")
//        users.current.city = city
    }

}

//fun TakeBookingOrder(order: BookingOrder): State = state {
//
//}