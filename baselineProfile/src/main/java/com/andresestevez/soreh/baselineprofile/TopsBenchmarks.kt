package com.andresestevez.soreh.baselineprofile

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class benchmarks the speed of app startup.
 * Run this benchmark to verify how effective a Baseline Profile is.
 * It does this by comparing [CompilationMode.None], which represents the app with no Baseline
 * Profiles optimizations, and [CompilationMode.Partial], which uses Baseline Profiles.
 *
 * Run this benchmark to see startup measurements and captured system traces for verifying
 * the effectiveness of your Baseline Profiles. You can run it directly from Android
 * Studio as an instrumentation test, or run all benchmarks with this Gradle task:
 * ```
 * ./gradlew :baselineprofile:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=Macrobenchmark
 * ```
 *
 * You should run the benchmarks on a physical device, not an Android emulator, because the
 * emulator doesn't represent real world performance and shares system resources with its host.
 *
 * For more information, see the [Macrobenchmark documentation](https://d.android.com/macrobenchmark#create-macrobenchmark)
 * and the [instrumentation arguments documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args).
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class TopsBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun topsCompilationNone() =
        benchmark(CompilationMode.None())

    @Test
    fun topsCompilationBaselineProfiles() =
        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun benchmark(compilationMode: CompilationMode) {
        rule.measureRepeated(
            packageName = "com.andresestevez.soreh",
            metrics = listOf(FrameTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 1,
            setupBlock = {
                pressHome()
            },
            measureBlock = {
                startActivityAndWait()

                val buttonBottomNavigationTops = device.findObject(By.desc("TOPS"))
                buttonBottomNavigationTops.click()

                val buttonMarvel = device.findObject(By.desc("Tab 1"))
                buttonMarvel.click()

                device.wait(Until.hasObject(By.desc("Hyperion")), 4000)
                device.findObject(By.desc("Hyperion")).click()

                device.wait(Until.hasObject(By.text("Combat")), 2000)

            }
        )
    }
}