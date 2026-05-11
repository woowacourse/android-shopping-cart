package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import woowacourse.shopping.repository.AndroidShoppingDatabase
import woowacourse.shopping.repository.DefaultProductRepository
import woowacourse.shopping.repository.DefaultShoppingCartRepository
import woowacourse.shopping.repository.DatabaseViewedProductRepository
import woowacourse.shopping.repository.ProductRemoteDataSource
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ViewedProductRepository

class ShoppingApplication : Application() {
    private val mockWebServer by lazy { startMockWebServer() }

    private val database by lazy {
        Room.databaseBuilder(
                applicationContext,
                AndroidShoppingDatabase::class.java,
                "shopping-db",
            ).addMigrations(AndroidShoppingDatabase.MIGRATION_1_2)
            .build()
    }

    val productRepository: ProductRepository by lazy {
        DefaultProductRepository(
            ProductRemoteDataSource(MOCK_SERVER_BASE_URL),
        )
    }

    val shoppingCartRepository: ShoppingCartRepository by lazy {
        DefaultShoppingCartRepository(productRepository, database.shoppingCartItemDao())
    }

    val viewedProductRepository: ViewedProductRepository by lazy {
        DatabaseViewedProductRepository(productRepository, database.viewedProductDao())
    }

    override fun onCreate() {
        super.onCreate()
        Thread {
            mockWebServer.start(12345)
        }.start()
    }
}

private const val MOCK_SERVER_BASE_URL = "http://127.0.0.1:12345"

private fun startMockWebServer(): MockWebServer {
    val mockWebServer = MockWebServer()

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.url.encodedPath) {
                "/products" -> {
                    val offset = request.url.queryParameter("offset")?.toIntOrNull() ?: 0
                    val size = request.url.queryParameter("size")?.toIntOrNull() ?: 30
                    val products =
                        (1..30).drop(offset).take(size).joinToString(
                            prefix = "[",
                            postfix = "]",
                            separator = ",",
                        ) { index ->
                            productJson(index)
                        }

                    MockResponse.Builder()
                        .code(200)
                        .setHeader("Content-Type", "application/json")
                        .body(products)
                        .build()
                }

                else -> {
                    val productId = request.url.pathSegments.lastOrNull()?.toIntOrNull()
                        ?: return MockResponse.Builder()
                            .code(404)
                            .build()

                    if (request.url.pathSegments.firstOrNull() == "products" && productId in 1..30) {
                        MockResponse.Builder()
                            .code(200)
                            .setHeader("Content-Type", "application/json")
                            .body(productJson(productId))
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

private fun productJson(productId: Int): String =
    """
    {
        "id": $productId,
        "name": "아메리카노$productId",
        "price": ${productId * 1000},
        "imageUrl": "https://bizimg.giftishow.com/Resource/goods/2025/G00003320983/G00003320983.jpg"
    }
    """.trimIndent()
