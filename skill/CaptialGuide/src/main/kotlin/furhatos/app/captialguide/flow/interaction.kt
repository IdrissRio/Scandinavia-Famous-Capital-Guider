package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.captialguide.nlu.*
import furhatos.nlu.wikidata.City

val Start: State = state(Interaction) {

    onEntry {
        furhat.say("Hi there.")
        goto(CityOptions)
    }

}

val End: State = state {
    onEntry {
        val bookings = users.current.information.bookings
        if (!bookings.isEmpty()) {
            furhat.say("You have booked")
            users.current.information.bookings.forEach {
                furhat.say(it)
            }
        }
        furhat.say("Goodbye and have a nice day")
        goto(Idle)
    }
}

fun DummyCityOptions(): State = state(CityOptions) {
    onEntry {
        furhat.ask("Is there another city you would like to visit?")

    }

    onResponse<Yes> {
        goto(CityOptions)
    }


    onResponse<No> {
        goto(End)
    }
}

val CityOptions = state(Interaction) {

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

    onResponse<ChangeCity> {
        goto(DummyCityOptions())
    }

    onResponse<ListBookedActivities> {
        val bookings = users.current.information.bookings
        if (bookings.isEmpty()) {
            furhat.say("You have no booked activities.")
        } else {
            furhat.say("You have booked")
            users.current.information.bookings.forEach {
                furhat.say(it)
            }
        }
        reentry()
    }

}

fun Options(): State = state(CityOptions) {

    onEntry {

        furhat.ask("Would you like some information about ${users.current.information.city.toName()} or book an activity?")
    }

    onReentry {
        furhat.ask("Would you like some more information about ${users.current.information.city.toName()} or book an activity?")
    }

    onResponse<BookActivity> {
        goto(SuggestBookings(users.current.information.city))
    }

    onResponse<GetInformation> {
        goto(CityInformation)
//        propagate()
    }

    onResponse<No> {
        goto(End)
    }


}

fun SuggestBookings(city: CityWithBooking): State = state(Options()) {

    onEntry {
        val activities = users.current.information.city.getActivities()
        furhat.say("Your activity options are")
        activities.forEach {
            furhat.say(it)
        }
        furhat.ask("Is there an activity would you like to book?")

    }

    onReentry {
        furhat.ask("Would you like to book another activity?")
    }

    onResponse(city.activityIntents) {
        furhat.say("Done! ${it.intent} has been booked!")
        users.current.information.bookings.add(it.intent.toString())
        reentry()
    }

    onResponse<ListActivites> {
        val activities = users.current.information.city.getActivities()
        activities.forEach {
            furhat.say(it)
        }
        reentry()
    }

    onResponse<No> {
        goto(DummyCityOptions())
    }

}


val CityInformation = state {

    onEntry {
        furhat.say("I will tell you a fact")
        val city = users.current.information.city
        furhat.say(city.tellHistory())
        goto(Options())
    }


}

fun CityRecieved(city: City): State = state {
    onEntry {
        try {
            users.current.information.city = Class.forName("furhatos.app.captialguide." + city.name).kotlin.constructors.first().call(city) as CityWithBooking
        } catch (e: java.lang.ClassNotFoundException) {
            furhat.say("Sorry, I don't have any information about ${city.name}")
            goto(DummyCityOptions())
        }
        goto(Options())
    }

}

