package game

import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.button
import react.dom.input
import react.dom.p
import styled.StyledComponents.css
import styled.css
import styled.styledB
import styled.styledButton
import styled.styledDiv
import kotlin.browser.document
import kotlin.random.Random

external interface DiceRollerProps : RProps {
    var onRollHandler : (Boolean) -> Unit
    var rollResult : Boolean?
}

/*external interface DiceRollerState : RState {
}*/

fun RBuilder.diceRoller(handler : DiceRollerProps.() -> Unit) : ReactElement {
    return child(DiceRoller::class) {
        this.attrs(handler)
    }
}

class DiceRoller : RComponent<DiceRollerProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css { display = Display.flex }
            p {
                +"Challenge Level"
                input(type = InputType.number) {
                    attrs {
                        id = "challengeLevelInput"
                        /*onChangeFunction = {
                            challengeLevel = (it.target as HTMLInputElement).value.toInt()
                        }*/
                    }
                }
                child(Keypad::class) {
                    attrs.onKeyHit = {
                        (document.getElementById("challengeLevelInput") as HTMLInputElement).value = it.toString()
                    }
                }
            }
            p {
                +"Dices"
                input(type = InputType.number) {
                    //+props.dices.toString()
                    attrs {
                        id = "diceInput"
                        /*onChangeFunction = {
                            dices = (it.target as HTMLInputElement).value.toInt()
                        }*/
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
                    val diceN = (document.getElementById("diceInput") as HTMLInputElement).value.toInt()
                    val challengeLevel = (document.getElementById("challengeLevelInput") as HTMLInputElement).value.toInt()
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
            console.log(props.rollResult)
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
