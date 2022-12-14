# Tagging Viewer

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](LICENSE.md)

This simple library lets you visualize live on-screen all the tagging that is occurring in your application.

This helps debug your tagging implementation.

## Example

| Overlay                                   | Detail                                     |
|-------------------------------------------|--------------------------------------------|
| <img width="280" src="art/overlay.png" /> | <img width="280" src="art/detailed.png" /> |


# Download

```groovy
dependencies {
  debugImplementation 'com.adevinta.android:tagging-viewer:+'
  releaseImplementation 'com.adevinta.android:tagging-viewer-no-op:+'
}
```

(Check for latest version in GitHub releases)

## 1. Register trackings

```kotlin
class MyCustomTracker {

  fun trackScreen(screenName: String) {
    // Fabric, Firebase, etc...
    TaggingViewer.tagScreen(screenName)
  }

  fun trackClick(clickName: String) {
    // Fabric, Firebase, etc...
    TaggingViewer.tagClick(clickName)
  }

  fun trackEvent(eventName: String) {
    // Fabric, Firebase, etc...
    TaggingViewer.tagEvent(eventkName)
  }
}
```

## 2. Display trackings

Whenever you want to show the tracked information enable the overlay from any Activity:

```kotlin
TaggingViewer.enableOverlay(context)
```

Alternatively, you can open a detailed screen with all tracked events and their extra information:

```kotlin
TaggingViewer.showDetailedActivity(context)
```

## 3. Styling

We are definitely not designers but we try our best. If you don't feel comfortable with the proposed design you only have to override the
following colors in your app to make it beautiful and show your colleagues how much sense of art you do have!

```xml

<color name="tgv_item_tracking_shadow_color">#fff</color><color name="tgv_item_tracking_name_background_color">#C4FFFFFF</color><color
name="tgv_item_tracking_name_text_color">#000
</color><color name="tgv_item_tracking_indicator_background_color">#89ffffff</color><color
name="tgv_item_separator_tracking_name_background_color">#89000000
</color><color name="tgv_view_background_color">#00000000</color><color name="tgv_item_tracking_event_indicator_color">#FFA000</color><color
name="tgv_item_tracking_screen_indicator_color">#303F9F
</color><color name="tgv_item_tracking_click_indicator_color">#388E3C</color><color name="tgv_detail_background">#F1F1F1</color><color
name="tgv_detail_timeline_line">#B1B1B1
</color>
```

Remember: You just have to define the color with the same name in your app and you are done!

# Contributing

If you find it useful and need more features or want to improve it, feel free to send a PR!
