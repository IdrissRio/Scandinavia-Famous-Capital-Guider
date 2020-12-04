package furhatos.app.captialguide.flow

import furhatos.app.captialguide.Stockholm
import furhatos.flow.kotlin.*
import furhatos.nlu.wikidata.City
import furhatos.util.*
import java.io.File
import java.nio.file.FileSystems

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}

val Interaction: State = state {

    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Start)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter() {
//        furhat.glance(it)]
        val current_user = users.current

        furhat.attend(it)
        random(
                { furhat.say("I'll be with you shortly, please have seat!") },
                { furhat.say("Please leave.... now!") }
        )
        furhat.attend(current_user)
        reentry()
    }

}