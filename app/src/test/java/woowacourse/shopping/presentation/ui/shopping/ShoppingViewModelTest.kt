package woowacourse.shopping.presentation.ui.shopping

import io.mockk.junit5.MockKExtension
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.remote.MockProductApiService
import woowacourse.shopping.data.remote.RemoteShoppingItemsRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.cart.FakeCartRepositoryImpl
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ShoppingViewModelTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var shoppingRepository: ShoppingItemsRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentlyViewedProductsRepository: RecentlyViewedProductsRepository

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val productApiService = MockProductApiService(mockWebServer)
        shoppingRepository = RemoteShoppingItemsRepository(productApiService)
        cartRepository = FakeCartRepositoryImpl()
        recentlyViewedProductsRepository = FakeRecentlyProductsRepository()
        viewModel =
            ShoppingViewModel(
                shoppingRepository,
                cartRepository,
                recentlyViewedProductsRepository,
            )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `처음 상품 목록을 부를 때 오류가 있다면 Error 상태로 변경한다`() {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Error::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 없다면 Empty 상태로 변경한다`() {
        // Given
        val emptyResponse = "[]"
        mockWebServer.enqueue(MockResponse().setBody(emptyResponse).setResponseCode(200))

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Empty::class.java)
    }

    @Test
    fun `처음 상품 목록을 부를 때 데이터가 있다면 Success 상태로 변경한다`() {
        // Given
        val responseBody =
            """
            [
                {
                    "id": 1,
                    "name": "치킨",
                    "price": 10000,
                    "imageUrl": "http://example.com/chicken.jpg"
                },
                {
                    "id": 2,
                    "name": "피자",
                    "price": 20000,
                    "imageUrl": "http://example.com/pizza.jpg"
                }
            ]
            """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
    }

    @Test
    fun `처음 상품 목록 20개를 불러온다`() {
        // Given
        val responseBody =
            (1..20).joinToString(",") {
                """
                {
                    "id": $it,
                    "name": "상품 $it",
                    "price": ${it * 1000},
                    "imageUrl": "http://example.com/product$it.jpg"
                }
            """
            }.let { "[$it]" }
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
        if (state is UIState.Success) {
            Assertions.assertEquals(20, state.data.size)
        }
    }

    @Test
    fun `더보기 버튼을 클릭하면 20개의 상품이 더 보인다`() {
        // Given
        val responseBody =
            (1..20).joinToString(",") {
                """
                {
                    "id": $it,
                    "name": "상품 $it",
                    "price": ${it * 1000},
                    "imageUrl": "http://example.com/product$it.jpg"
                }
            """
            }.let { "[$it]" }
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        viewModel.loadProducts()
        viewModel.onLoadMoreButtonClick()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
        if (state is UIState.Success) {
            Assertions.assertEquals(20, state.data.size)
        }
    }

    @Test
    fun `데이터가 20개보다 작다면 그 데이터의 수만큼 보인다`() {
        // Given
        val responseBody =
            (1..17).joinToString(",") {
                """
                {
                    "id": $it,
                    "name": "상품 $it",
                    "price": ${it * 1000},
                    "imageUrl": "http://example.com/product$it.jpg"
                }
            """
            }.let { "[$it]" }
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // When
        viewModel.loadProducts()
        viewModel.onLoadMoreButtonClick()
        viewModel.onLoadMoreButtonClick()

        // Then
        val state = viewModel.productItemsState.getOrAwaitValue()
        assertThat(state).isInstanceOf(UIState.Success::class.java)
        if (state is UIState.Success) {
            Assertions.assertEquals(17, state.data.size)
        }
    }
}
