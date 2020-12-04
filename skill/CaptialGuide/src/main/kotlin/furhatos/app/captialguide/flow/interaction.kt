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

fun DummyCityOptions(): State = state(CityOptions) {
    onEntry {
        furhat.ask("Is there another city you would like to visit?")
        goto(CityOptions)
    }
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

    onResponse<ChangeCity> {
        goto(DummyCityOptions())
    }

}

val BookingOptions = state {

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
        goto(Idle)
    }


}

fun SuggestBookings(city: CityWithBooking): State = state(CityOptions) {

    onEntry {
        val activities = users.current.information.city.getActivities()
        furhat.say("Your activity options are")
        activities.forEach {
            furhat.say(it)
        }
        furhat.ask("Which activity would you like to book?")

    }

    onReentry {
        furhat.ask("Would you like to book another activity?")
    }

    onResponse(city.activityIntents) {
        furhat.say("Done! ${it.intent} has been booked!")
        reentry()
    }

    onResponse<ListActivites> {
        val activities = users.current.information.city.getActivities()
        activities.forEach {
            furhat.say(it)
        }
        reentry()
    }

}


//fun BookingReceived(activity: BookActivity): State = state(Options) {
//    onEntry {
//        furhat.say("${fruitList.text}, what a lovely choice!")
//        fruitList.list.forEach {
//            users.current.order.fruits.list.add(it)
//        }
//        furhat.ask("Anything else?")
//    }
//
//    onReentry {
//        furhat.ask("Did you want something else?")
//    }
//
//    onResponse<No> {
//        furhat.say("Okay, here is your order of ${users.current.order.fruits}. Have a great day!")
//        goto(Idle)
//    }
//}

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
        }
        catch (e: java.lang.ClassNotFoundException){
            furhat.say("Sorry, I don't have any information about ${city.name}")
            goto(DummyCityOptions())
        }
        goto(Options())
    }

}

