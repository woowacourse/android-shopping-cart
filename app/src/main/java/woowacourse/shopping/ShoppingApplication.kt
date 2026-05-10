package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import woowacourse.shopping.repository.AndroidShoppingDatabase
import woowacourse.shopping.repository.DatabaseProductRepository
import woowacourse.shopping.repository.DatabaseShoppingCartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository

class ShoppingApplication : Application() {
    private val mockWebServer by lazy { startMockWebServer() }

    private val database by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                AndroidShoppingDatabase::class.java,
                if (BuildConfig.DEBUG) "debug-shopping-db" else "release-shopping-db",
            ).apply {
                if (BuildConfig.DEBUG) {
                    createFromAsset("shopping-cart.db")
                }
            }.build()
    }

    val productRepository: ProductRepository by lazy {
        DatabaseProductRepository(database.productDao())
    }

    val shoppingCartRepository: ShoppingCartRepository by lazy {
        DatabaseShoppingCartRepository(productRepository, database.shoppingCartItemDao())
    }

    override fun onCreate() {
        super.onCreate()
        Thread {
            mockWebServer.start(12345)
        }.start()
    }
}

private fun startMockWebServer(): MockWebServer {
    val mockWebServer = MockWebServer()
    val products = (1..30).joinToString(
        prefix = "[",
        postfix = "]",
        separator = ",",
    ) { index ->
        """
        {
            "id": $index,
            "name": "아메리카노$index",
            "price": ${index * 1000},
            "imageUrl": "https://bizimg.giftishow.com/Resource/goods/2025/G00003320983/G00003320983.jpg"
        }
        """.trimIndent()
    }


    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.url.encodedPath) {
                "/products" -> MockResponse.Builder()
                    .code(200)
                    .setHeader("Content-Type", "application/json")
                    .body(products)
                    .build()

                else -> {
                    val productId = request.url.pathSegments.lastOrNull()?.toIntOrNull()
                        ?: return MockResponse.Builder()
                            .code(404)
                            .build()

                    if (request.url.pathSegments.firstOrNull() == "products" && productId in 1..30) {
                        val product = """
                {
                    "id": $productId,
                    "name": "아메리카노$productId",
                    "price": ${productId * 1000},
                    "imageUrl": "https://bizimg.giftishow.com/Resource/goods/2025/G00003320983/G00003320983.jpg"
                }
            """.trimIndent()

                        MockResponse.Builder()
                            .code(200)
                            .setHeader("Content-Type", "application/json")
                            .body(product)
                            .build()
                    } else {
                        MockResponse.Builder()
                            .code(404)
                            .build()
                    }
                }
            }
        }
    }

    mockWebServer.dispatcher = dispatcher
    return mockWebServer
}
