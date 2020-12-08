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
        var furhatName = "My name is "
        random(
                {furhat.say("Hi there!")},
                {furhat.say("Hello there!")},
                {furhat.say("Hello!")},
                {furhat.say("Hi!")}
        )
        val lastCity = available_cities.last()
        furhat.say("I have information about ${available_cities.take(available_cities.size - 1).joinToString(" ")} and $lastCity")
        goto(CityOptions)
    }

}



val End: State = state {
    onEntry {
        if(users.current.information.hasActivities()){
            furhat.say{"Before you leave, a brief recap of your booked activities"}
        }
            for((k,v) in users.current.information.cityActivities){
                if(v.isNotEmpty()){
                    random(
                            {furhat.say("In $k you booked activities in the following place:" )},
                            { furhat.say("In $k you have booked activities at:") },
                            { furhat.say("In $k your booked activities are:") }
                    )

                    v.forEach {
                        furhat.say(it)
                    }
                }
        }
        random(
                {furhat.say("Goodbye and have a nice day")},
                {furhat.say("Bye and have a nice day")},
                {furhat.say("Byebye and have a nice day")},
                {furhat.say("Have a nice day, bye")},
                {furhat.say("Have a nice day, byebye")},
                {furhat.say("Thanks! Goodbye!")},
                {furhat.say("Thanks! Byebye!")},
                {furhat.say("Byebye!")},
                {furhat.say("Goodbye!")}
        )
        goto(Idle)
    }
}

fun DummyCityOptions(): State = state(CityOptions) {
    onEntry {
        random(
                {furhat.ask("Is there another city you would like to visit?")},
                {furhat.ask("Do you want to go somewhere else?")},
                {furhat.ask("Would you like to go somewhere else?")},
                {furhat.ask("Do you want to visit somewhere else?")},
                {furhat.ask("Would you like to visit somewhere else?")},
                {furhat.ask("Do you want to go to another city?")},
                {furhat.ask("Would you like to visit another city?")},
                {furhat.ask("Do you want to visit another city?")}
        )
    }

    onReentry {
        random(
                {furhat.ask("Is there another city you would like to visit?")},
                {furhat.ask("Do you want to go somewhere else?")},
                {furhat.ask("Would you like to go somewhere else?")},
                {furhat.ask("Do you want to visit somewhere else?")},
                {furhat.ask("Would you like to visit somewhere else?")},
                {furhat.ask("Do you want to go to another city?")},
                {furhat.ask("Would you like to visit another city?")},
                {furhat.ask("Do you want to visit another city?")}
        )
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
        random(
                {furhat.ask("Which city are you interested in?")},
                {furhat.ask("Which city would you like to go?")},
                {furhat.ask("where would you like to visit?")},
                {furhat.ask("where do want to travel?")},
                {furhat.ask("Which city do you want to visit?")},
                {furhat.ask("Is there a city you want to visit?")}
        )
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
        var isCompletelyEmpty = true
        for((k,v) in users.current.information.cityActivities) {
            if (v.isNotEmpty()) {
                random(
                        {furhat.say("In $k you booked activities in the following place:" )},
                        { furhat.say("In $k you have booked activities at:") },
                        { furhat.say("In $k your booked activities are:") }
                )
                isCompletelyEmpty = false
                v.forEach {
                    furhat.say(it)
                }
            }
        }
        if(isCompletelyEmpty){
            random(
                    {furhat.say("You have no booked activities.")},
                    {furhat.say("You have not booked any activities.")},
                    {furhat.say("No activity has been booked.")},
                    {furhat.say("There is no activity has been booked.")}
            )
        }
        reentry()
    }

}

fun Options(): State = state(CityOptions) {

    onEntry {
        random(
                {furhat.ask("Would you like some information about ${users.current.information.city.toName()} or book an activity?")},
                {furhat.ask("Do you want to book an activity or know some information about ${users.current.information.city.toName()}?")},
                {furhat.ask("some information about ${users.current.information.city.toName()} or book an activity?")}
        )
    }

    onReentry {
        random(
                {furhat.ask("Would you like some information about ${users.current.information.city.toName()} or book an activity?")},
                {furhat.ask("Do you want to book an activity or know some information about ${users.current.information.city.toName()}?")}
        )
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
        random(
                {furhat.say("Your activity options in ${city.city.name} are ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!")},
                {furhat.say("In ${city.city.name}, we have ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!")},
                {furhat.say("We have ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!, in ${city.city.name}")},
                {furhat.say("Your choices are ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!, in ${city.city.name}")},
                {furhat.say("You can choose ${activities.take(activities.size - 1).joinToString(", ")} or $lastActivity!, in ${city.city.name}")}
                )
        random(
                {furhat.ask("Is there an activity you would like to book?")},
                {furhat.ask("Which one do you want to book?")}
        )
    }

    onReentry {
        if (users.current.information.hasActivities()) {
            random(
                    {furhat.ask("Would you like to book another activity?")},
                    {furhat.ask("Would you like to book one more?")},
                    {furhat.ask("One more?")},
                    {furhat.ask("Any others?")},
                    {furhat.ask("Is that done?")}
            )
        } else {
            random(
                    {furhat.ask("Is there an activity in ${city.city.name} you would like to book?")},
                    {furhat.ask("Which activity do you want to book ${city.city.name} ?")},
                    {furhat.ask("which one would you like to book in ${city.city.name}?")}
            )
        }

    }

    onResponse(city.getIntents()) {
        random(
                {furhat.say("Done! ${it.intent} has been booked!")},
                {furhat.say("All right! Now you have booked ${it.intent}!")},
                {furhat.say("All right! ${it.intent} has been booked!")},
                {furhat.say("Now you have ${it.intent}!")}
        )
        addActivityToCity(city.toName(), it.intent.toString(), users.current.information.cityActivities)
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

fun addActivityToCity(city: String, activity:String, cityActivities: HashMap<String,MutableList<String>>) {
    var setOfActivities = cityActivities[city]
    if(setOfActivities == null){
        setOfActivities = mutableListOf()
    }
    setOfActivities.add(activity)
    cityActivities[city] = setOfActivities
}

fun RecievedCityAndActivity(city: City, activity: Activity?) = state {

    onEntry {
        try {
            users.current.information.city = Class.forName("furhatos.app.captialguide." + city.name).kotlin.constructors.first().call(city) as CityWithBooking
            val city = users.current.information.city
            val cityBookedActivities = (city.getIntents() as EnumEntity).getEnum(Language.ENGLISH_US)
            if (activity != null) {
                if (cityBookedActivities.joinToString(" ").contains(activity.toString(), ignoreCase = true)) {
                    furhat.say("$activity has been booked in ${city.toName()}")
                    addActivityToCity(city.toName(), activity.toString(), users.current.information.cityActivities)
                } else {
                    random(
                            {furhat.say("Sorry, I do not have a record of that activity in ${city.toName()}")},
                            {furhat.say("Sorry, I do not have it in ${city.toName()}")}
                    )
                }
                goto(Options())
            } else {
                random(
                        {furhat.say("Sorry, I did not catch what activity you would like to book in ${city.toName()}")},
                        {furhat.say("Sorry, which activity in ${city.toName()}? I did not catch it")}
                )
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
        random(
                {furhat.say("I will tell you a fact")},
                {furhat.say("I will tell you something interesting")},
                {furhat.say("Here is something interesting")},
                {furhat.say("Here is a piece of fact")},
                {furhat.say("Here is a piece of information")}
        )
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

