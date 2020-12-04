package furhatos.app.captialguide

import furhatos.app.captialguide.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class CaptialguideSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
