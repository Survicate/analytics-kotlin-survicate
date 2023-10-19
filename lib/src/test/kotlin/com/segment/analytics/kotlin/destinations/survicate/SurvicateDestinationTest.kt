package com.segment.analytics.kotlin.destinations.survicate

import android.content.Context
import com.segment.analytics.kotlin.core.IdentifyEvent
import com.segment.analytics.kotlin.core.ScreenEvent
import com.segment.analytics.kotlin.core.Settings
import com.segment.analytics.kotlin.core.TrackEvent
import com.segment.analytics.kotlin.core.emptyJsonObject
import com.segment.analytics.kotlin.core.platform.Plugin
import com.survicate.surveys.Survicate
import com.survicate.surveys.traits.UserTrait
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.jvm.kotlinFunction

class SurvicateDestinationTest {

    private val anyContext: Context = mockk()
    private val applicationContext: Context = mockk()
    private val tested = SurvicateDestination(context = anyContext)

    @BeforeEach
    fun setup() {
        mockSetWorkspaceKey()
        mockInit()
    }

    @Test
    fun `initializes Survicate SDK at initial update`() {
        val integrationSettings = Settings(
            integrations = buildJsonObject {
                put("Survicate", buildJsonObject {
                    put("workspaceKey", "testKey")
                })
            }
        )

        tested.update(integrationSettings, Plugin.UpdateType.Initial)

        verify { Survicate.setWorkspaceKey("testKey") }
        verify { Survicate.init(any()) }
    }

    @Test
    fun `does not initialize Survicate SDK on Refresh update`() {
        val integrationSettings = Settings(
            integrations = buildJsonObject {
                put("Survicate", buildJsonObject {
                    put("workspaceKey", "testKey")
                })
            }
        )

        tested.update(integrationSettings, Plugin.UpdateType.Refresh)

        verify(exactly = 0) { Survicate.setWorkspaceKey("testKey") }
        verify(exactly = 0) { Survicate.init(any()) }
    }

    @Test
    fun `notifies Survicate about all user traits when identify event recorded`() {
        mockSetUserTraits()
        val identifyEvent = IdentifyEvent(
            userId = "testUserId",
            traits = buildJsonObject {
                put("name", "John Doe")
                put("age", 50)
            }
        )

        tested.identify(identifyEvent)

        val expectedTraits = listOf(
            UserTrait.UserId("testUserId"),
            UserTrait("name", "John Doe"),
            UserTrait("age", "50")
        )
        verify { Survicate.setUserTraits(expectedTraits) }
    }

    @Test
    fun `notifies Survicate about event when track event recorded`() {
        mockInvokeEvent()
        val trackEvent = TrackEvent(
            properties = emptyJsonObject,
            event = "testEvent"
        )

        tested.track(trackEvent)

        verify { Survicate.invokeEvent("testEvent") }
    }

    @Test
    fun `notifies Survicate about screen enter when screen event recorded`() {
        mockEnterScreen()
        val screenEvent = ScreenEvent(
            name = "testScreen",
            category = "",
            properties = emptyJsonObject
        )

        tested.screen(screenEvent)

        verify { Survicate.enterScreen("testScreen") }
    }

    @Test
    fun `resets Survicate on reset call`() {
        mockReset()

        tested.reset()

        verify { Survicate.reset() }
    }

    private fun mockSetWorkspaceKey() {
        mockkStatic(Survicate::setWorkspaceKey)
        every { Survicate.setWorkspaceKey(any()) } answers { }
    }

    private fun mockInit() {
        val ref = Survicate::class.java.declaredMethods
            .mapNotNull { it.kotlinFunction }
            .first { it.name == "init" && it.parameters.count() == 1 }

        mockkStatic(ref)

        every { Survicate.init(any()) } answers { }
        every { anyContext.applicationContext } returns applicationContext
    }

    private fun mockSetUserTraits() {
        mockkStatic(Survicate::setUserTraits)
        every { Survicate.setUserTraits(any()) } answers { }
    }

    private fun mockInvokeEvent() {
        mockkStatic(Survicate::invokeEvent)
        every { Survicate.invokeEvent(any()) } answers { }
    }

    private fun mockEnterScreen() {
        mockkStatic(Survicate::enterScreen)
        every { Survicate.enterScreen(any()) } answers { }
    }

    private fun mockReset() {
        mockkStatic(Survicate::reset)
        every { Survicate.reset() } answers { }
    }

}
