package com.andresestevez.soreh.framework.logs

import com.andresestevez.soreh.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class LogsAndCrashReporting @Inject constructor(
    private val debugTree: SorehDebugTree,
    private val crashReportingTree: CrashReportingTree
) {
    fun setup() {
        if (BuildConfig.DEBUG) Timber.plant(debugTree)
        else Timber.plant(crashReportingTree)
    }
}