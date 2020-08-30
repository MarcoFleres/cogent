package game

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput

external interface KeypadProps : RProps {
    var onValueChange : ((Int?) -> Unit)?
    var startingValue : Int?
}

external interface KeypadState : RState {
    var inputValue : String
}

class Keypad : RComponent<KeypadProps, KeypadState>() {
    init {
        console.log("init keypad con propiedades")
        setState {
            inputValue = props.startingValue.toString()
        }
    }

    override fun KeypadState.init() {
        console.log("init keypad sin propiedades")
        inputValue = ""
    }

    override fun KeypadState.init(props: KeypadProps) {
        console.log("init keypad con propiedades")
        inputValue = props.startingValue.toString()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.inlineGrid
                gridTemplateColumns = GridTemplateColumns("100px 100px 100px")
                gridTemplateAreas = GridTemplateAreas("\"input input input\" \"b1 b2 b3\" \"b4 b5 b6\" \"b7 b8 b9\" \"b0 b0 clear\"")
            }
            styledInput(type=InputType.number) {
                css { put("grid-area", "input")}
                attrs {
                    value = state.inputValue
                    onChangeFunction = {
                        console.log("Changed")
                        val target = it.target as HTMLInputElement
                        setState {
                          inputValue = target.value
                          props.onValueChange?.invoke(inputValue.toInt())
                        }
                    }
                }
            }
            (0..9).forEach { num ->
                styledButton {
                    css { put("grid-area", "b$num") }
                    +num.toString()
                    attrs {
                        onClickFunction = {
                            setState {
                                inputValue+=num
                                props.onValueChange?.invoke(inputValue.toInt())
                            }
                        }
                    }
                }
            }
            styledButton {
                css {put("grid-area", "clear")}
                +"␡"
                attrs.onClickFunction = {
                    setState {
                        inputValue = ""
                        props.onValueChange?.invoke(null)
                    }
                }
            }
        }
    }
}
