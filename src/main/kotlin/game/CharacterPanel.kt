package game

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledCode
import styled.styledDiv
import styled.styledP

external interface CharacterInfoPanelProps : RProps {
    var currentCharacter : Character
    var onDiceCheckSelected : (Int) -> Unit
}

external interface SkillButtonProp : RProps {
    var character : Character
    var attribute : Attribute
    var onDiceCheckSelected : (Int) -> Unit
}

class SkillButton : RComponent<SkillButtonProp, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            button {
                +"\uD83C\uDFB2"
                //+"${props.attribute.name}(${props.character.attributes.getOrElse(props.attribute, {0})})"
                attrs {
                    onClickFunction = {
                        console.log("${props.attribute}: ${props.character.diceAmount(props.attribute)}")
                        props.onDiceCheckSelected(props.character.diceAmount(props.attribute))
                    }
                }
            }
            p {
                +props.attribute.name
            }
            p {
                +"${props.character.attributes.getOrElse(props.attribute, {0})}"
            }
        }
    }
}

fun RBuilder.skillButton(handler: SkillButtonProp.() -> Unit): ReactElement {
    return child(SkillButton::class) {
        this.attrs(handler)
    }
}

class CharacterInfoPanel : RComponent<CharacterInfoPanelProps, RState>() {
    override fun RBuilder.render() {
        val c = props.currentCharacter
        div {
            +"Level: ${c.level }"
            fieldSet {
                legend { +"Skills" }
                +"STR: ${c.skills.getOrElse(Skill.Strength, {0})}"
                +"REF: ${c.skills.getOrElse(Skill.Reflex, {0})}"
                +"INT: ${c.skills.getOrElse(Skill.Intelligence, {0})}"
            }
            styledCode {
                css {
                    display = Display.flex
                    children("fieldSet") {
                        verticalAlign = VerticalAlign.middle
                    }
                }


                Attribute.values().groupBy { it.skills.toString() }.forEach {
                    fieldSet {
                        legend { +it.key }
                        it.value.forEach { att ->
                            //p {
                                skillButton {
                                    character = c
                                    attribute = att
                                    onDiceCheckSelected = props.onDiceCheckSelected
                                }
                            //}
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.characterInfoPanel(handler: CharacterInfoPanelProps.() -> Unit) : ReactElement {
    return child(CharacterInfoPanel::class) {
        this.attrs(handler)
    }
}
