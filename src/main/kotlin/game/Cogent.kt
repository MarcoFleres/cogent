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
    var diceAmount : Int
}

external interface DiceIndicatorProps : RProps {
    var success : Boolean?
}

val diceIndicator = functionalComponent<DiceIndicatorProps> { props ->
    p { props.success }
}

class CogentMain : RComponent<CogentMainProps, CogentMainState>() {

    override fun CogentMainState.init() {
        characterList = (1..5).map { Character.randomCharacter() }.toMutableList()
        diceAmount = 0
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
                child(DiceRoller::class) {
                    attrs {
                        rollResult = state.rollResult
                        startingChallengeLevel = 0
                        startingDices = state.diceAmount
                        onRollHandler = { roll ->
                            setState {
                                rollResult = roll
                            }
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
                            setState {
                                diceAmount = d
                                console.log("Actualizado el dice amount a $diceAmount")
                            }
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
