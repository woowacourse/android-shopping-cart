package com.example.domain.remote

import com.example.domain.datasource.firstJsonProducts
import com.example.domain.datasource.secondJsonProducts
import com.example.domain.datasource.thirdJsonProducts
import com.example.domain.model.Price
import com.example.domain.model.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray

class MockProductRemoteService {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val baseUrl: String

    init {
        mockWebServer.url("/") // 포트번호 랜덤
        baseUrl =
            String.format("http://localhost:%s", mockWebServer.port)

        // 디스패쳐 모드로 응답을 결정
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products?lastProductId=0" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(firstJsonProducts)
                    }
                    "/products?lastProductId=20" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(secondJsonProducts)
                    }
                    "/products?lastProductId=40" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(thirdJsonProducts)
                    }
                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

        mockWebServer.dispatcher = dispatcher
    }


    fun request(lastProductId: Long): List<Product> {
        val okHttpClient = OkHttpClient()
        val url = "$baseUrl/products?lastProductId=$lastProductId"
        val request = Request.Builder().url(url).build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            response.close()
            throw RuntimeException("Request failed with HTTP error code: ${response.code}")
        }

        val responseBody = response.body?.string()
        response.close()

        responseBody?.let {
            return parseJsonToProductList(it)
        } ?: return emptyList()
    }

    private fun parseJsonToProductList(responseString: String): List<Product> {
        val productList = mutableListOf<Product>()

        val jsonArray = JSONArray(responseString)
        for (i in 0 until jsonArray.length()) {
            val jsonProduct = jsonArray.getJSONObject(i)
            val id = jsonProduct.getInt("id")
            val name = jsonProduct.getString("name")
            val imageUrl = jsonProduct.getString("imageUrl")
            val jsonPrice = jsonProduct.getInt("price")

            val price = Price(jsonPrice)
            val product = Product(id.toLong(), name, imageUrl, price)
            productList.add(product)
        }

        return productList
    }
}