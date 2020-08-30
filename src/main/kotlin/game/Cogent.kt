package game

import ComponentStyles
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.h2
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledH1
import kotlin.browser.document

external interface CogentMainProps : RProps {
}

external interface CogentMainState: RState {
    var characterList : MutableList<Character>
    var selectedCharacter: Character?
    var rollResult : Boolean?
}

external interface DiceIndicatorProps : RProps {
    var success : Boolean?
}

val diceIndicator = functionalComponent<DiceIndicatorProps> { props ->
    p { props.success }
}

class DiceIndicator : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        TODO("Not yet implemented")
    }
}


class CogentMain : RComponent<CogentMainProps, CogentMainState>() {

    override fun CogentMainState.init() {
        characterList = (1..5).map { Character.randomCharacter() }.toMutableList()
    }

    override fun RBuilder.render() {
        styledDiv {
            css { +ComponentStyles.root }

            styledH1 {
                +"Cogent"
                css { +ComponentStyles.title }
            }
            div {
                h2 { +"Dice Roller" }
                diceRoller {
                    rollResult = state.rollResult
                    onRollHandler = { roll ->
                        setState {
                            rollResult = roll
                            console.log(rollResult)
                        }
                    }
                }
            }
            child(diceIndicator) {
                attrs.success = state.rollResult
            }

            styledDiv {
                h2 { +"Characters" }
                styledDiv {

                    characterList {
                        characterList = state.characterList
                        currentCharacter = state.selectedCharacter
                        onSelectCharacter = { character ->
                            setState {
                                selectedCharacter = character
                            }
                        }
                        onDiceCheckSelected = { d ->
                            (document.getElementById("diceInput") as HTMLInputElement).value = d.toString()
                        }
                        onAddCharacter = {
                            setState {
                                characterList.add(Character.randomCharacter())
                            }
                        }
                    }
                }
            }
        }
    }
}

