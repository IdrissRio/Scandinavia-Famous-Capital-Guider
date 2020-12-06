package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.app.captialguide.available_cities
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.captialguide.nlu.*
import furhatos.nlu.DynamicIntent
import furhatos.nlu.EnumEntity
import furhatos.nlu.wikidata.City
import furhatos.snippets.getExamplesString
import furhatos.util.Language

val Start: State = state(Interaction) {

    onEntry {
        furhat.say("Hi there!")
        val lastCity = available_cities.last()
        furhat.say("I have information about ${available_cities.take(available_cities.size - 1).joinToString(" ")} and $lastCity")
        goto(CityOptions)
    }

}

val End: State = state {
    onEntry {
        val bookings = users.current.information.bookings
        if (bookings.isNotEmpty()) {
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

    onReentry {
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
        furhat.ask("Which city are you interested in?")
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

    onResponse<BookActivityInCity> {
        val city = it.intent.city
        val activity = it.intent.activity
        if (city != null) {
            goto(RecievedCityAndActivity(city = city, activity = activity))
        } else {
            propagate()
        }
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
        if (it.intent.activity == null) {
            goto(SuggestBookings(users.current.information.city))
        } else {
            call(RecievedCityAndActivity(users.current.information.city.city, it.intent.activity))
        }

    }

    onResponse<GetInformation> {
        goto(CityInformation)
    }

    onResponse<No> {
        goto(DummyCityOptions())
    }
}


fun SuggestBookings(city: CityWithBooking): State = state(Options()) {

    onEntry {
        val activities = users.current.information.city.activities
        val lastActivity = activities.last()
        furhat.say("Your activity options in ${city.city.name} are ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!")
        furhat.ask("Is there an activity you would like to book?")
    }

    onReentry {
        if (users.current.information.bookings.isNotEmpty()) {
            furhat.ask("Would you like to book another activity?")
        } else {
            furhat.ask("Is there an activity in ${city.city.name} you would like to book?")
        }

    }

    onResponse(city.getIntents()) {
        furhat.say("Done! ${it.intent} has been booked!")
        users.current.information.bookings.add(it.intent.toString())
        reentry()
    }


    onResponse<ListActivites> {
        val activities = users.current.information.city.activities
        activities.forEach {
            furhat.say(it)
        }
        reentry()
    }

    onResponse<No> {
        goto(DummyCityOptions())
    }
}

fun RecievedCityAndActivity(city: City, activity: Activity?) = state {

    onEntry {
        try {
            users.current.information.city = Class.forName("furhatos.app.captialguide." + city.name).kotlin.constructors.first().call(city) as CityWithBooking
            val city = users.current.information.city
            val cityActivities = (city.getIntents() as EnumEntity).getEnum(Language.ENGLISH_US)
            if (activity != null) {
                if (cityActivities.joinToString(" ").contains(activity.toString(), ignoreCase = true)) {
                    furhat.say("$activity has been booked in ${city.toName()}")
                    users.current.information.bookings.add(activity.toString())
                } else {
                    furhat.say("Sorry, I do not have a record of that activity in ${city.toName()}")
                }
                goto(Options())
            } else {
                furhat.say("Sorry, I did not catch what activity you would like to book in ${city.toName()}")
                goto(SuggestBookings(city = city))
            }
        } catch (e: java.lang.ClassNotFoundException) {
            furhat.say("Sorry, I don't have any information about ${city.name}")
        }
        goto(DummyCityOptions())
    }

}


val CityInformation = state {

    onEntry {
        furhat.say("I will tell you a fact")
        val city = users.current.information.city
        val fact = city.facts.shuffled().takeLast(1)[0]
        furhat.say(fact)
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

