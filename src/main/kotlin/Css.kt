import kotlinx.css.*
import kotlinx.css.Display.grid
import kotlinx.css.Display.inlineGrid
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import styled.StyleSheet

object ComponentStyles : StyleSheet("ComponentStyles") {
    val root by css {
        children("h1, h2, h3, h4, h5, h6") {
            textDecoration(TextDecorationLine.underline)
            textAlign = TextAlign.center
        }
        children("div") {
            border = "ridge"
            padding = "5px"
        }
    }

    val title by css {
        textAlign = TextAlign.center
        color = Color.white
        backgroundColor = Color.black
    }
}
