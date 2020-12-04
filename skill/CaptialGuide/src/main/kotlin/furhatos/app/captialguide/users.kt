package furhatos.app.captialguide.flow
import furhatos.records.User
import furhatos.app.captialguide.nlu.CityName
import furhatos.nlu.wikidata.City

class UserInfromation (
        var city: City = City()
)

val User.information : UserInfromation
    get() = data.getOrPut(UserInfromation::class.qualifiedName, UserInfromation())

//var User.city : CityName?
//    get() = data.getOrPut(CityName::class.qualifiedName, CityName())
////    set(CityName?: value) = {value}
    