package com.andresestevez.soreh.framework.workers

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.andresestevez.soreh.AppModule
import com.andresestevez.soreh.data.server.MockServerDispatcher
import com.andresestevez.soreh.framework.local.CharacterDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class CacheWorkerTest {

    private lateinit var context: Context
    private lateinit var server: MockWebServer

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cacheWorkerFactory: HiltWorkerFactory

    @Inject
    lateinit var dao: CharacterDao

    @Before
    fun setUp() {
        server = MockWebServer()
        server.dispatcher = MockServerDispatcher().RequestDispatcher()
        server.start(8080)

        hiltRule.inject()

        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun cacheWorker_doWork_populatesBD(): Unit = runTest {

        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<CacheWorker>(context = context)
            .setWorkerFactory(cacheWorkerFactory).build()

        // check that the DB is empty
        assertEquals(0, dao.count())

        // Start the work synchronously
        val future = worker.startWork()
        val result = future.get()

        // check that worker result is success and the DB contains all the characters retrieved from the server
        assertEquals(ListenableWorker.Result.Success(), result)
        assertEquals(732, dao.count())

    }


}