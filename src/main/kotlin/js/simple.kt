package js

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.get


fun main() {
    console.log("Hello, ${greet()}")

    window.addEventListener("load", {
        console.log("page loaded")
        init()
    })
}

fun buttonListener(event: Event) {
    val eventTarget = event.target as HTMLInputElement

    showInput(eventTarget.value)
}

fun showInput(value: String) {
    val resultElement = document.getElementById("result") as HTMLInputElement
    val list = resultElement.defaultValue.toCharArray().toList().map { it.toString() }
    if (list.contains("=")) {
        resultElement.defaultValue = ""
    }
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

fun addOperation(value: String) {
    val resultElement = document.getElementById("result") as HTMLInputElement
    val operationList = listOf("+", "-", "*", "/", "C")
    val listOfTextStrings = resultElement.defaultValue.toMutableList().map { it.toString() }

    if (!operationList.any { listOfTextStrings.contains(it) }&&listOfTextStrings.isNotEmpty()) {
        resultElement.defaultValue = resultElement.defaultValue + value
    }
}

fun countExpression(inputTextValue: String) {
    if (inputTextValue.isNotEmpty()) {
        val resultElement = document.getElementById("result") as HTMLInputElement
        val result = eval(inputTextValue).toString()
        resultElement.defaultValue = "$inputTextValue=$result"
    }
}

fun init() {
    val elementsByTagName = document.getElementsByTagName("input")
    val resultElement = document.getElementById("result") as HTMLInputElement
    resultElement.defaultValue = ""
    for (i in 0 until elementsByTagName.length) {
        val element1 = elementsByTagName[i]
        val element = element1 as HTMLInputElement
        if (element.type == "button") {
            element.addEventListener("click", { buttonListener(it) })
        }
    }
}

fun greet() = "kotlin2"