# Analytics-Kotlin Survicate
[Survicate](https://survicate.com/) is an all-in-one customer feedback platform that helps you collect and act on feedback from your customers. It helps you understand your customers and improve their experience with your product or service.

Add Survicate device mode support to your applications using this plugin for [Analytics-Kotlin](https://github.com/segmentio/analytics-kotlin).

## Adding the dependency

Define Maven repository
```
allprojects {
    repositories {
        // ...
        maven { url 'https://repo.survicate.com' }
    }
}
```

Add dependency to your app's build.gradle file

```
dependencies {
    // ...
    implementation 'com.survicate:survicate-segment-analytics-kotlin:<latest_version>'
}
```

Note that there is no need to include Survicate library, because it will be added as a transitive dependency.

## Configuration

First you need to provide Survicate workspace key.

You can do this in 2 ways:

1. Add workspace key in Segment panel -> Destinations -> Your Android app destination -> Settings. You need to put the key inside the Connection Settings as a "Workspace Key".

2. Alternatively, you can add your Survicate workspace key as a metadata inside AndroidManifest

   ```xml
   <application
       android:name=".MyApp"
   >
       <!-- ... -->
       <meta-data android:name="com.survicate.surveys.workspaceKey" android:value="YOUR_WORKSPACE_KEY"/>
   </application>
   ```	
## Usage
In order to activate the Survicate plugin, you have to add a `SurvicateDestination` to the Analytics instance.

```kotlin
analytics = Analytics(segmentKey, applicationContext) {  
  // ...
}
analytics.add(SurvicateDestination(applicationContext))
```

Make sure to keep only one instance of the `Analytics` (e.g. initialize it inside Application's onCreate method). This is important, because the Survicate SDK underneath can only be initialized once.

Now you can use the Analytics as usual, having the events mapped to Survicate SDK as described below.

**Events mapping**

_**identify**_

In the SurvicateDestination plugin, the `identify` event from Segment is transferred to the `setUserTraits` method of Survicate. You can provide multiple key-value pairs. The `userId` argument of `Analytics.identify` is also set as a user trait with the key "user_id".

_**track**_

The `track` method from Segment is used as the `invokeEvent` method in Survicate. This means that when you track an event in Segment, it will be invoked in Survicate.

_**screen**_

Similarly, the screen method from Segment is used as the `enterScreen` method in Survicate. This means that when you track a screen in Segment, it will be entered in Survicate.

_**reset**_

The `reset` method from Segment is used as the reset method in Survicate. This means that when you reset the Segment client, the Survicate client will also be reset.
