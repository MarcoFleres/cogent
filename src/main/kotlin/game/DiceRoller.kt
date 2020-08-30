package game

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import react.dom.p
import styled.css
import styled.styledButton
import styled.styledDiv
import kotlin.random.Random

external interface DiceRollerProps : RProps {
    var onRollHandler : (Boolean) -> Unit
    var rollResult : Boolean?
    var startingChallengeLevel : Int?
    var startingDices : Int?
}

external interface DiceRollerState : RState {
    var challengeLevel : Int
    var dices : Int
}

class DiceRoller : RComponent<DiceRollerProps, DiceRollerState>() {

    override fun DiceRollerState.init() {
        console.log("Redibujado el dice roller")
        challengeLevel = 0
        dices = 0
    }

    override fun DiceRollerState.init(props: DiceRollerProps) {
        console.log("Redibujado el dice roller: ${props.startingDices}")
        challengeLevel = props.startingChallengeLevel?:0
        dices = props.startingDices?:0
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                children("div p") {
                    textAlign = TextAlign.center
                }
            }
            div {
                p {+"Challenge Level"}
                div {
                    child(Keypad::class) {
                        attrs {
                            key = "challengeLevel"
                            startingValue = props.startingChallengeLevel
                            onValueChange = {
                                setState {
                                    challengeLevel = it ?: 0
                                }
                            }
                        }
                    }
                }
            }
            div {
                p{+"Dices"}
                div {
                    child(Keypad::class) {
                        attrs {
                            key = "dices"
                            startingValue = props.startingDices
                            onValueChange = {
                                setState {
                                    dices = it ?: 0
                                }
                            }
                        }
                    }
                }
            }
        }
        styledButton {
            +"Roll"
            css {
                props.rollResult?.let {
                    color = if(it) Color.green else Color.red
                }
            }
            attrs {
                onClickFunction = {
                    val diceN = state.dices
                    val challengeLevel = state.challengeLevel
                    val roll = challenge(
                            challengeLevel,
                            diceN
                    )
                    console.log("CL: ${challengeLevel} D: ${diceN} => ${roll}")
                    props.onRollHandler(roll)
                }
            }
        }
        p {
            key="Result Indicator"
            when (props.rollResult) {
                true -> +"SUCCESS!"
                false -> +"FAIL!"
            }
        }

    }

    fun diceRoll(n : Int) : Int {
        require(n >= 0)
        return (0..n).reduce { acum, _ -> acum + if(Random.nextBoolean()) 1 else 0 }
    }

    fun challenge(cl: Int, diceN : Int) : Boolean = diceRoll(diceN) >= cl
}

fun RBuilder.diceRoller(handler : DiceRollerProps.() -> Unit) : ReactElement {
    return child(DiceRoller::class) {
        this.attrs(handler)
    }
}
