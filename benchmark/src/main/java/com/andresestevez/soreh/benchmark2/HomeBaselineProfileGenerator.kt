package com.andresestevez.soreh.benchmark2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RequiresApi(Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeBaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        packageName = "com.andresestevez.soreh",
    ){
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

        device.wait(Until.hasObject(By.desc("Card 2")), 15000)


    }

}