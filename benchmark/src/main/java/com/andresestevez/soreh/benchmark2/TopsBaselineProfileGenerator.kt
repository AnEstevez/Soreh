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
class TopsBaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()


    @Test
    fun generate() = rule.collect(
        packageName = "com.andresestevez.soreh",
    ){
        pressHome()
        startActivityAndWait()

        val buttonBottomNavigationTops = device.findObject(By.desc("TOPS"))
        buttonBottomNavigationTops.click()

        device.wait(Until.hasObject(By.desc("Tab 1")), 3000)

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
}