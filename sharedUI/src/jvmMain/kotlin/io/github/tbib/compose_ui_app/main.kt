package io.github.tbib.compose_ui_app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeUIApp",
    ) {
        App()
    }
}