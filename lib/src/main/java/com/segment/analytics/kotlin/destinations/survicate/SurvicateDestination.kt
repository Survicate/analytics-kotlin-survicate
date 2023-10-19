package com.segment.analytics.kotlin.destinations.survicate

import android.content.Context
import com.segment.analytics.kotlin.core.BaseEvent
import com.segment.analytics.kotlin.core.IdentifyEvent
import com.segment.analytics.kotlin.core.ScreenEvent
import com.segment.analytics.kotlin.core.Settings
import com.segment.analytics.kotlin.core.TrackEvent
import com.segment.analytics.kotlin.core.platform.DestinationPlugin
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.utilities.safeJsonPrimitive
import com.survicate.surveys.Survicate
import com.survicate.surveys.traits.UserTrait
import kotlinx.serialization.Serializable

class SurvicateDestination(private val context: Context) : DestinationPlugin() {
    override val key: String = "Survicate"

    override fun update(settings: Settings, type: Plugin.UpdateType) {
        super.update(settings, type)
        if (type == Plugin.UpdateType.Initial) {
            val workspaceKey = settings.destinationSettings<SurvicateSettings>(key)?.workspaceKey
            if (workspaceKey != null) {
                Survicate.setWorkspaceKey(workspaceKey)
            }
            Survicate.init(context.applicationContext)
        }
    }

    override fun identify(payload: IdentifyEvent): BaseEvent {
        val userIdTrait = UserTrait.UserId(payload.userId)
        val otherTraits = payload.traits
            .filter { it.value.safeJsonPrimitive != null }
            .map { UserTrait(it.key, it.value.safeJsonPrimitive!!.content) }

        Survicate.setUserTraits(listOf(userIdTrait) + otherTraits)

        return payload
    }

    override fun track(payload: TrackEvent): BaseEvent {
        Survicate.invokeEvent(payload.event)
        return payload
    }

    override fun screen(payload: ScreenEvent): BaseEvent {
        Survicate.enterScreen(payload.name)
        return payload
    }

    override fun reset() {
        Survicate.reset()
    }
}

@Serializable
data class SurvicateSettings(
    val workspaceKey: String
)
