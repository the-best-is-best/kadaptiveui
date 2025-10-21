<h1 align="center">KADaptiveUI</h1><br>
<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=24" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
  <img src="https://img.shields.io/badge/iOS-12%2B-blue.svg?logo=apple" alt="iOS 12+ Badge" />

<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
</div>

### KAdaptiveUI is a Kotlin Multiplatform library that provides a set of adaptive UI components for Android and iOS using Jetpack Compose and UIKit It helps developers write once and run adaptive UI for both platforms with platform-specific look and feel

<hr>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/kadaptiveui)](https://central.sonatype.com/artifact/io.github.the-best-is-best/kadaptiveui)

KAdmob is available on `mavenCentral()`.

## Install

Add dependency to your build.gradle.kts:

```kotlin
implementation("io.github.the-best-is-best:kadaptiveui:1.2.0")
```

## Components

### 1. AdaptiveCircularProgressIndicator

```kotlin
AdaptiveCircularProgressIndicator()
```

### 2. AdaptiveTile

```kotlin
AdaptiveTile(
    title = "Settings",
    subtitle = "Manage preferences",
    leadingIcon = adaptiveIcon,
    trailingIcon = adaptiveIcon,
    onClick = {}
)
```

### 3. AdaptiveAppBar

```kotlin
AdaptiveAppBar(
    title = "Home",
    actions = listOf(
        AdaptiveActionItem("Search", adaptiveIcon) { /* onClick */ }
    )
)
```

### 4. AdaptiveBottomNavBar

```kotlin
AdaptiveBottomNavBar(
    items = listOf(
        AdaptiveBottomNavItem("Home", adaptiveIcon, true),
        AdaptiveBottomNavItem("Profile", adaptiveIcon, false)
    ),
    onItemSelected = { /* index */ }
)
```

### 5. AdaptiveBottomSheet

```kotlin
AdaptiveBottomSheet(
    state = rememberAdaptiveSheetState(),
    content = { Text("Sheet content") }
)
```

### 6. AdaptiveDatePicker

```kotlin
AdaptiveDatePicker(
    state = rememberAdaptiveDatePickerState(),
    onDateSelected = { date -> }
)
```

### 7. AdaptiveDialog

```kotlin
AdaptiveDialog(
    onDismissRequest = { },
    title = "Confirm",
    text = "Are you sure?",
    confirmButtonText = "OK",
    dismissButtonText = "Cancel",
    onConfirm = { }
)
```

### 8. AdaptiveIconButton

```kotlin
AdaptiveIconButton(
    onClick = {},
    adaptiveIcons = adaptiveIcon
)
```

### 9. AdaptiveSlider

```kotlin
AdaptiveSlider(
    value = 0.5f,
    onValueChange = { }
)
```

### 10. AdaptiveTextButton

```kotlin
AdaptiveTextButton(
    text = "Continue",
    onClick = { }
)
```

### 11. AdaptiveTimePicker

```kotlin
AdaptiveTimePicker(
    state = rememberAdaptiveTimePickerState(),
    onTimeSelected = { time -> }
)
```

### 12. AdaptiveSwitch

```kotlin
AdaptiveSwitch(
    checked = true,
    onCheckedChange = { }
)
```

## License

MIT License
