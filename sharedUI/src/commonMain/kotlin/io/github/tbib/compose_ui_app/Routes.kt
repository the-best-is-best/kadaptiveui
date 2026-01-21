package io.github.tbib.compose_ui_app

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Details : Routes


}
