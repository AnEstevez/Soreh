package com.andresestevez.soreh.ui.search

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.espresso.Espresso
import com.andresestevez.soreh.AppModule
import com.andresestevez.soreh.MainActivity
import com.andresestevez.soreh.common.Helpers
import com.andresestevez.soreh.data.server.MockServerDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SearchScreenEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var server: MockWebServer

    @Inject
    lateinit var helper: Helpers

    @Before
    fun setUp() {
        server = MockWebServer()
        server.dispatcher = MockServerDispatcher().RequestDispatcher()
        server.start(8080)

        hiltRule.inject()

        runTest {
            helper.populateBDFromRemote()
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun searchScreen_findCharactersByNameAndSelectBlackFlash_navigatesToDetail() : Unit =
        with(composeTestRule) {
            onNodeWithContentDescription("SEARCH").performClick()

            onNodeWithContentDescription("Search").assertExists()
            onNodeWithContentDescription("Search").performClick()
            onNodeWithContentDescription("Search").performTextInput("black")
            onNodeWithContentDescription("Search").assertTextEquals("black")
            onNodeWithContentDescription("Search").performKeyPress(
                KeyEvent(
                    NativeKeyEvent(100, 100, ACTION_DOWN, Key.Enter.nativeKeyCode, 1)
                )
            )

            onNode(hasScrollToIndexAction()).performScrollToIndex(6)
            onNodeWithContentDescription("Black Flash").assertIsDisplayed()
            onNodeWithContentDescription("Black Flash").performClick()

            onRoot().printToLog("searchScreen_findCharactersByNameAndSelectBlackFlash_navigatesToDetail")
            // Character stats shown
            onAllNodesWithText("Power").onFirst().assertIsDisplayed()

        }


    @Test
    fun searchScreen_backFromDetailScreen_restoresContentInSearchScreen() : Unit =
        with(composeTestRule) {
            onNodeWithContentDescription("SEARCH").performClick()

            onNodeWithContentDescription("Search").assertExists()
            onNodeWithContentDescription("Search").performClick()
            onNodeWithContentDescription("Search").performTextInput("black")
            onNodeWithContentDescription("Search").assertTextEquals("black")
            onNodeWithContentDescription("Search").performKeyPress(
                KeyEvent(
                    NativeKeyEvent(100, 100, ACTION_DOWN, Key.Enter.nativeKeyCode, 1)
                )
            )

            onNode(hasScrollToIndexAction()).performScrollToIndex(6)
            onNodeWithContentDescription("Black Flash").assertIsDisplayed()
            onNodeWithContentDescription("Black Flash").performClick()

            // Character stats shown
            onAllNodesWithText("Power").onFirst().assertIsDisplayed()
            Espresso.pressBack()
            onNode(hasScrollToIndexAction()).performScrollToIndex(6)

            // Content restored in search screen
            onNodeWithContentDescription("Black Flash").assertIsDisplayed()

        }




}