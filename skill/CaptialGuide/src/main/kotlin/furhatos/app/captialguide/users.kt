package furhatos.app.captialguide.flow

import furhatos.app.captialguide.CityWithBooking
import furhatos.records.User
import furhatos.nlu.wikidata.City



class UserInformation(
        var cityActivities:HashMap<String,MutableList<String>> = HashMap<String,MutableList<String>>(),
        var city: CityWithBooking = CityWithBooking(City())

){
    fun hasActivities():Boolean{
        var hasActivities = true
        for((_,v) in cityActivities){
            hasActivities = hasActivities && v.isNotEmpty()
        }
        return hasActivities
    }
}

val User.information: UserInformation
    get() = data.getOrPut(UserInformation::class.qualifiedName, UserInformation())

    