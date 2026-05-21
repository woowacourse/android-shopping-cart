package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.repository.cart.RoomCartRepository
import woowacourse.shopping.repository.product.HttpProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.repository.recent_product.RoomRecentProductRepository
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private lateinit var mockWebServer: MockWebServer

    val database by lazy {
        Room.databaseBuilder(this, ShoppingDatabase::class.java, "shopping.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val cartRepository by lazy {
        RoomCartRepository(
            dao = database.shoppingDao(),
            externalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        )
    }

    val recentProductRepository by lazy {
        RoomRecentProductRepository(
            dao = database.shoppingDao()
        )
    }

    lateinit var productRepository: ProductRepository
        private set

    override fun onCreate() {
        super.onCreate()
        startMockWebServer()
    }

    private fun startMockWebServer() {
        mockWebServer = MockWebServer()
        val products = """
                [
                    {
                        "id": 1,
                        "name": "딸기주스",
                        "price": 1000,
                        "imageUrl": "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc"
                    },
                    {
                        "id": 2,
                        "name": "무엘사",
                        "price": 1005,
                        "imageUrl": "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc"
                    },
                    {
                        "id": 3,
                        "name": "딸기주스 12개입",
                        "price": 1000055,
                        "imageUrl": "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc"
                    },
                    {
                        "id": 4,
                        "name": "딸기주스 12종 13개입",
                        "price": 104055,
                        "imageUrl": "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc"
                    },
                    {
                        "id": 5,
                        "name": "깁슨 레스폴",
                        "price": 4040525,
                        "imageUrl": "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc"
                    },
                    ${
            (6..60).joinToString(",\n") { id ->
                val index = (id - 1) % 5
                val names = listOf("딸기주스", "무엘사", "딸기주스 12개입", "딸기주스 12종 13개입", "깁슨 레스폴")
                val prices = listOf(1000, 1005, 1000055, 104055, 4040525)
                val images = listOf(
                    "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                    "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                    "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                    "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                    "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc"
                )
                """{ "id": $id, "name": "${names[index]} $id", "price": ${prices[index]}, "imageUrl": "${images[index]}" }"""
            }
        }
                ]
            """.trimIndent()

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products" -> {
                        MockResponse()
                            .setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody(products)
                    }

                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
        mockWebServer.dispatcher = dispatcher

        thread {
            mockWebServer.start(12345)
        }

        productRepository = HttpProductRepository("http://localhost:12345/")
    }

    override fun onTerminate() {
        super.onTerminate()
        mockWebServer.shutdown()
    }
}
