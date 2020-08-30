package game

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.css.CSSMarginRule
import react.*
import react.dom.button
import react.dom.div
import styled.*
import kotlin.browser.document

external interface KeypadProps : RProps {
    var onKeyHit : (Int) -> Unit
}

external interface KeypadState : RState {
    var inputValue : String
}

class Keypad : RComponent<KeypadProps, KeypadState>() {
    override fun KeypadState.init() {
        inputValue = ""
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
                }
            }
            (0..9).forEach {
                styledButton {
                    css { put("grid-area", "b$it") }
                    +it.toString()
                    attrs {
                        onClickFunction = {

                            /*
                            (it.target as HTMLInputElement).textContent?.let {
                                props.onKeyHit(it.toInt())
                            }*/
                        }
                    }
                }
            }
            styledButton {
                css {put("grid-area", "clear")}
                +"␡"
                attrs.onClickFunction = {

                }
            }
        }
    }
}