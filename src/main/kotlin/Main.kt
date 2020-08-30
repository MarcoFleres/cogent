import game.CogentMain
import react.dom.*
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
        child(CogentMain::class) {}
    }
}
