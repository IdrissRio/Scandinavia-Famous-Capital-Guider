package furhatos.app.captialguide

import furhatos.app.captialguide.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import java.io.File
import java.nio.file.FileSystems

val available_cities : MutableList<String> = mutableListOf()

class CaptialguideSkill : Skill() {
    override fun start() {
        val filename = FileSystems.getDefault().getPath("src/main/kotlin/furhatos/app/captialguide/cities.kt").normalize().toAbsolutePath().toString()
        val lines = File(filename).readLines()
        val regex = Regex(pattern="^class .*[(]")
        for (line in lines)
            if (regex.containsMatchIn(line)){
                val index = line.indexOf("(")
                available_cities.add(line.substring(6, index))
            }
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
