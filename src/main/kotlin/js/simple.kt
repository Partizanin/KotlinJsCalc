package js

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.get


fun main() {
    window.addEventListener("load", {
        console.log("page loaded")
        init()
    })
}

fun init() {
    val elementsByTagName = document.getElementsByTagName("input")
    val resultElement = getInputElement()
    resultElement.defaultValue = ""
    for (i in 0 until elementsByTagName.length) {
        val element = elementsByTagName[i] as HTMLInputElement
        if (element.type == "button") {
            element.addEventListener("click", { buttonListener(it) })
        }
    }
}

fun getInputElement() = document.getElementById("result") as HTMLInputElement

fun buttonListener(event: Event) {
    val eventTarget = event.target as HTMLInputElement
    showInput(eventTarget.value)
}

fun showInput(value: String) {
    val resultElement = getInputElement()
    val listOfInputChars = getListOfInputStrings(resultElement)
    if (listOfInputChars.contains("=")) resultElement.defaultValue = ""

    when (value) {
        "+" -> addOperation(value)
        "-" -> addOperation(value)
        "/" -> addOperation(value)
        "*" -> addOperation(value)
        "=" -> countExpression(resultElement.defaultValue)
        "C" -> resultElement.defaultValue = ""
        else -> resultElement.defaultValue = resultElement.defaultValue + value
    }
}

fun getListOfInputStrings(resultElement: HTMLInputElement): List<String> {
    return resultElement.defaultValue.toCharArray().toList().map { it.toString() }
}


fun addOperation(value: String) {
    val resultElement = getInputElement()
    val operationList = listOf("+", "-", "*", "/", "C")
    val listOfTextStrings = getListOfInputStrings(resultElement)

    if (!operationList.any { listOfTextStrings.contains(it) } && listOfTextStrings.isNotEmpty()) {
        resultElement.defaultValue = resultElement.defaultValue + value
    }
}

fun countExpression(inputTextValue: String) {
    if (inputTextValue.isNotEmpty()) {
        val resultElement = getInputElement()
        val result = eval(inputTextValue).toString()
        resultElement.defaultValue = "$inputTextValue=$result"
    }
}

fun greet() = "world"