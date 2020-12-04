package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.records.User
import furhatos.nlu.wikidata.City



class UserInformation(
        var city: CityWithBooking = CityWithBooking(City()),
        var bookings: MutableList<String> = mutableListOf()
)

val User.information: UserInformation
    get() = data.getOrPut(UserInformation::class.qualifiedName, UserInformation())

    