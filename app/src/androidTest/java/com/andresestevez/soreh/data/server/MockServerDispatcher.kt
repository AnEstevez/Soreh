package com.andresestevez.soreh.data.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {
    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path?.contains("/search/a") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameA.json"))
                request.path?.contains("/search/e") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameE.json"))
                request.path?.contains("/search/i") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameI.json"))
                request.path?.contains("/search/o") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameO.json"))
                request.path?.contains("/search/q") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameQ.json"))
                request.path?.contains("/search/s") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameS.json"))
                request.path?.contains("/search/t") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameT.json"))
                request.path?.contains("/search/u") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameU.json"))
                request.path?.contains("/search/x") == true -> MockResponse().setResponseCode(200).setBody(getJsonContent("charactersByNameX.json"))
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return error response from mock server
     */
    internal inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
