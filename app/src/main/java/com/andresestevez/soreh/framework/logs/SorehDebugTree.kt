package com.andresestevez.soreh.framework.logs

import timber.log.Timber
import javax.inject.Inject

class SorehDebugTree @Inject constructor() : Timber.DebugTree() {
    /**
     * Override [log] to modify the tag and add a "global tag" prefix to it. You can rename the String "global_tag_" as you see fit.
     */
    override fun log(
        priority: Int, tag: String?, message: String, t: Throwable?,
    ) {
        super.log(priority, "timber_$tag", message, t)
    }

    /**
     * Override [createStackElementTag] to include a append a "method name" to the tag.
     */
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "%s:%s:%s",
            element.fileName,
            element.methodName,
            element.lineNumber,
            super.createStackElementTag(element)
        )
    }
}