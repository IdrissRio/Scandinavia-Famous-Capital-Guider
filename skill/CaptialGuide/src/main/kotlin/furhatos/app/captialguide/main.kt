package furhatos.app.captialguide

import furhatos.app.captialguide.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.EnumEntity
import furhatos.nlu.wikidata.City
import furhatos.snippets.getExamplesString
import furhatos.util.Language
import java.io.File
import java.nio.file.FileSystems

val available_cities : MutableList<String> = mutableListOf()
val available_activities : MutableList<String> = mutableListOf()

class CaptialguideSkill : Skill() {
    override fun start() {
        val filename = FileSystems.getDefault().getPath("src/main/kotlin/furhatos/app/captialguide/cities.kt").normalize().toAbsolutePath().toString()
        val lines = File(filename).readLines()
        val regex = Regex(pattern="^class .*[(]")
        for (line in lines)
            if (regex.containsMatchIn(line)){
                val index = line.indexOf("(")
                val city = line.substring(6, index)
                val cityclass = Class.forName("furhatos.app.captialguide." + city).kotlin.constructors.first().call(City()) as CityWithBooking
                val cityActivities = (cityclass.getIntents() as EnumEntity).getEnum(Language.ENGLISH_US)
                available_activities.addAll(cityActivities)
                available_cities.add(city)
            }
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
