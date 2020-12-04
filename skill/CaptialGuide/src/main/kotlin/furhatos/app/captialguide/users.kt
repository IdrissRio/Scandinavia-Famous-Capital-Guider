package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.records.User
import furhatos.app.captialguide.nlu.CityName
import furhatos.nlu.SimpleIntent
import furhatos.nlu.wikidata.City

class UserInfromation(
        var city: CityWithBooking = CityWithBooking(City()),
        var bookings: MutableList<String> = mutableListOf()
)

val User.information: UserInfromation
    get() = data.getOrPut(UserInfromation::class.qualifiedName, UserInfromation())

    