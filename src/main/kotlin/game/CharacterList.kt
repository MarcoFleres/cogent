package game

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledDiv

external interface CharacterListProps : RProps {
    var characterList : MutableList<Character>
    var currentCharacter : Character?
    var onSelectCharacter: (Character) -> Unit
    var onDiceCheckSelected : (Int) -> Unit
    var onAddCharacter: () -> Unit
}

class CharacterList : RComponent<CharacterListProps, RState>() {
    override fun RBuilder.render() {

        styledDiv {
            css {
                +ComponentStyles.root
                display = Display.flex
            }
            styledDiv {
                css {
                    flex(0.2)
                }
                button {
                    +"+"
                    attrs {
                        onClickFunction = { props.onAddCharacter() }
                    }
                }
                for (c in props.characterList) {
                    styledDiv {
                        attrs {
                            onClickFunction = {
                                props.onSelectCharacter(c)
                            }
                        }
                        p {
                            if (c == props.currentCharacter) {
                                b { +"${c.name} (${c.level})" }
                            } else {
                                +"${c.name} (${c.level})"
                            }
                        }
                    }
                }
            }
            styledDiv {
                css {
                    flex(0.8)
                }
                props.currentCharacter?.let { character ->
                    characterInfoPanel {
                        currentCharacter = character
                        onDiceCheckSelected = props.onDiceCheckSelected
                    }
                }
            }
        }
    }
}

fun RBuilder.characterList(handler: CharacterListProps.() -> Unit): ReactElement {
    return child(CharacterList::class) {
        this.attrs(handler)
    }
}
