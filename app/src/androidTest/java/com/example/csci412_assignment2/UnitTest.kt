package com.example.csci412_assignment2

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UnitTest {

    private lateinit var device: UiDevice

    @Before
    fun setup() {
        // Initializing UiDevice
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
    }

    @Test
    @Throws(UiObjectNotFoundException::class)
    fun testLaunchAppAndActivity() {

        // Initialize context
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // Launch app through icon
        val appLabel = context.getString(R.string.app_name)  // Get the app name from string resources
        val appIcon: UiObject = device.findObject(UiSelector().text(appLabel))  // Find the app icon using text
        appIcon.clickAndWaitForNewWindow()

        // Click on the “Start Activity Explicitly” button
        val startActivityText = context.getString(R.string.explicit)
        val startActivityButton = device.findObject(UiSelector().text(startActivityText))
        assertTrue("Start activity button not found", startActivityButton.exists())
        startActivityButton.click()

        // Verify that a challenge is displayed
        val challengeTextContent = context.getString(R.string.c1)
        val challengeText = device.findObject(UiSelector().textContains(challengeTextContent))
        assertTrue("Challenge not displayed", challengeText.exists())
    }
}