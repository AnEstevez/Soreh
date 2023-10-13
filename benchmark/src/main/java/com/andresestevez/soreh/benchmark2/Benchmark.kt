package com.andresestevez.soreh.benchmark2

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class Benchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.andresestevez.soreh",
        metrics = listOf(StartupTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun topsScrollAndGoToDetail() = benchmarkRule.measureRepeated(
        packageName = "com.andresestevez.soreh",
        metrics = listOf(FrameTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.COLD,
    ) {
        pressHome()
        startActivityAndWait()

//        device.wait(Until.hasObject(By.desc("TOPS")), 8000)

        val buttonBottomNavigationTops = device.findObject(By.desc("TOPS"))
        buttonBottomNavigationTops.click()

        val buttonMarvel = device.findObject(By.desc("Tab 1"))
        buttonMarvel.click()

        val topList = device.findObject(By.scrollable(true))
        topList.setGestureMargin(device.displayWidth / 6)
        topList.fling(Direction.DOWN)
        topList.fling(Direction.DOWN)
        topList.fling(Direction.DOWN)

        device.findObject(By.desc("Magus")).click()

        device.wait(Until.hasObject(By.text("Combat")), 3000)

    }

    @Test
    fun homeGoToDetailAndSwipe() = benchmarkRule.measureRepeated(
        packageName = "com.andresestevez.soreh",
        metrics = listOf(FrameTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.COLD,
    ) {
        pressHome()
        startActivityAndWait()

        device.wait(Until.hasObject(By.desc("Carousel 0")), 15000)

        val carouselElement1 = device.findObject(By.desc("Carousel 0"))
        carouselElement1.click()

        device.wait(Until.hasObject(By.desc("Card 0")), 3000)

        val card1 = device.findObject(By.desc("Card 0"))
        card1.swipe(Direction.LEFT, 0.8f)

        val card2 = device.findObject(By.desc("Card 1"))
        card2.swipe(Direction.LEFT, 0.8f)

        val card3 = device.findObject(By.desc("Card 2"))
        card3.swipe(Direction.LEFT, 0.8f)
    }
}