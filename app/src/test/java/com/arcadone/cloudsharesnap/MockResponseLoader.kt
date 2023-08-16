package com.arcadone.cloudsharesnap

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import java.io.BufferedReader

object MockResponseLoader {
    private fun loadString(fileName: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream("mockResponse/$fileName")

        return inputStream!!.bufferedReader().use(BufferedReader::readText)
    }

    fun buildMockJsonResponse(filename: String): MockResponse {
        val body = loadString(filename)

        return buildMockResponse(body.toByteArray(), "application/json; charset=utf-8")
    }

    private fun buildMockResponse(responseBytes: ByteArray, responseContentType: String): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", responseContentType)
            .setBody(Buffer().readFrom(responseBytes.inputStream()))
    }

}
