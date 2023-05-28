package woowacourse.shopping.data.server

import android.util.Log
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.ProductMockWebServer
import woowacourse.shopping.data.util.ShoppingOkHttpClient
import woowacourse.shopping.domain.Product
import java.lang.Exception
import java.lang.Thread.sleep

class ProductServiceImpl : ProductService {
    private var products: List<Product> = mutableListOf()
    private lateinit var mockWebServer: MockWebServer

    init {
        Thread {
            request(
                { products = it },
                { Log.d("test", "실패 ${it.message}") },
            )
        }.start()
        while (products.isEmpty()) sleep(1)
    }

    fun get(): List<Product> {
        return products.toList()
    }

    override fun findAll(): List<Product> {
        return products
    }

    override fun find(id: Int): Product {
        return products[id]
    }

    override fun findRange(mark: Int, rangeSize: Int): List<Product> {
        if (products.size <= mark + rangeSize) {
            return products.subList(mark, products.lastIndex)
        }
        return products.subList(mark, mark + rangeSize)
    }

    override fun isExistByMark(mark: Int): Boolean {
        return products.size > mark
    }

    private fun request(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        synchronized(this) {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = ProductMockWebServer().getDispatcher()
            mockWebServer.url("/")

            val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
            val url = "$baseUrl/products"
            ShoppingOkHttpClient.enqueue(url, onSuccess, onFailure)
        }
    }
}
