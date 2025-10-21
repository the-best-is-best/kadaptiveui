package io.github.tbib.kadaptiveui.bottom_sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue

import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException

@OptIn(ExperimentalMaterial3Api::class)
@Stable
actual class AdaptiveSheetState actual constructor(
    skipPartiallyExpanded: Boolean?,
    density: Density,
    initialValue: SheetValue,
    confirmValueChange: (SheetValue) -> Boolean,
    skipHiddenState: Boolean,
) {
    private val _skipPartiallyExpanded = skipPartiallyExpanded ?: false

    init {
        if (_skipPartiallyExpanded) {
            require(initialValue != SheetValue.PartiallyExpanded) {
                "The initial value must not be set to PartiallyExpanded if skipPartiallyExpanded " +
                        "is set to true."
            }
        }
        if (skipHiddenState) {
            require(initialValue != SheetValue.Hidden) {
                "The initial value must not be set to Hidden if skipHiddenState is set to true."
            }
        }
    }

    val materialSheetState = SheetState(
        skipPartiallyExpanded = _skipPartiallyExpanded,
        skipHiddenState = skipHiddenState,
        initialValue = initialValue,
        confirmValueChange = { confirmValueChange(it) },
        positionalThreshold = { with(density) { 56.dp.toPx() } }, // default threshold for movement
        velocityThreshold = { with(density) { 125.dp.toPx() } }    // default fling velocity threshold
    )

    actual val currentValue: SheetValue get() = materialSheetState.currentValue
    actual val isVisible get() = materialSheetState.isVisible

    /**
     * Expand the bottom sheet with animation and suspend until it is [PartiallyExpanded] if defined
     * else [Expanded].
     * @throws [CancellationException] if the animation is interrupted
     */
    actual suspend fun show() {
        materialSheetState.show()
    }

    /**
     * Hide the bottom sheet with animation and suspend until it is fully hidden or animation has
     * been cancelled.
     * @throws [CancellationException] if the animation is interrupted
     */
    actual suspend fun hide() {
        materialSheetState.hide()
    }

    actual companion object {
        /**
         * The default [Saver] implementation for [AdaptiveSheetState].
         */
        actual fun Saver(
            skipPartiallyExpanded: Boolean?,
            confirmValueChange: (SheetValue) -> Boolean,
            density: Density
        ) = Saver<AdaptiveSheetState, SheetValue>(
            save = { it.currentValue },
            restore = { savedValue ->
                AdaptiveSheetState(
                    skipPartiallyExpanded,
                    density,
                    savedValue,
                    confirmValueChange,
                    false
                )
            }
        )
    }
}