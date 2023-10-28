package com.andresestevez.soreh.framework.logs

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import javax.inject.Inject

class CrashReportingTree @Inject constructor(private val crashlytics: FirebaseCrashlytics) :
    Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            when (t) {
                null -> crashlytics.recordException(Throwable(message))
                else -> crashlytics.recordException(t)
            }
        }
    }

}