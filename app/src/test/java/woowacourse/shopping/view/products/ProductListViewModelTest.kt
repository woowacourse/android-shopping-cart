package woowacourse.shopping.view.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.FakeRecentItemRepository
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.data.product.mockserver.MockProductService
import woowacourse.shopping.data.product.mockserver.MockServer
import woowacourse.shopping.data.product.mockserver.MockServerProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import woowacourse.shopping.view.ProductUpdate
import kotlin.concurrent.thread

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentViewedItemRepository: RecentViewedItemRepository

    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        thread {
            MockServer.mockWebServer.start(12345)
        }.join()

        cartRepository = FakeCartRepository()
        recentViewedItemRepository = FakeRecentItemRepository()
        productRepository = MockServerProductRepository(MockProductService())
        viewModel =
            ProductListViewModel(
                productRepository,
                cartRepository,
                recentViewedItemRepository,
            )
    }

    @AfterEach
    fun tearDown() {
        thread {
            MockServer.mockWebServer.shutdown()
        }.join()
    }

    @Test
    fun `정해진_데이터_로딩_양만큼_상품_데이터를_로드한다`() {
        val result = viewModel.products.getOrAwaitValue()
        assertThat(result.items.size).isEqualTo(20)
    }

    @Test
    fun `초기_장바구니_총_수량_데이터를_로드한다`() {
        val result = viewModel.totalCount.getOrAwaitValue()
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `초기_최근_본_상품_데이터들을_로드한다`() {
        val result = viewModel.recentViewed.getOrAwaitValue()
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `상품을_장바구니에_담으면_장바구니_아이템의_총_수량이_증가한다`() {
        viewModel.onIncreaseQuantityButtonClicked(product0)

        val totalCartItemCount = viewModel.totalCount.getOrAwaitValue()
        assertThat(totalCartItemCount).isEqualTo(1)

        val updatedProductResult = viewModel.updateProductCount.getOrAwaitValue()
        val expected = ProductUpdate(productId = 0L, updatedQuantity = 1)
        assertThat(updatedProductResult).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_담았던 상품을_취소하면_장바구니_아이템의_총_수량이_감소한다`() {
        viewModel.onIncreaseQuantityButtonClicked(product0)

        viewModel.onDecreaseQuantityButtonClicked(product0)

        val totalCartItemCount = viewModel.totalCount.getOrAwaitValue()
        assertThat(totalCartItemCount).isEqualTo(0)

        val updatedProductResult = viewModel.updateProductCount.getOrAwaitValue()
        val expected = ProductUpdate(productId = 0L, updatedQuantity = 0)
        assertThat(updatedProductResult).isEqualTo(expected)
    }

    private val product0 =
        Product(
            id = 0L,
            imageUrl = "https://s3-alpha-sig.figma.com/img/6a52/2b6a/05b81120d274b875b55d6da04de4749e?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bF~PKj~RVd3Rg-U5LFz7izxp92X8QuDAiYxB6S4iXS41nq6BXyKGCmyZdn39WHzQIeIscToVmlFB7WwRKhnfDR-WxMbd7dTuJIRhwiR8iQ5lq6LUm7MLNEv9l779WDsICq0kUJp1MUPVJFDG72HKGqMJaL5KuqlmJeOhlT2Qy1rneIXyjuILXnqAbS56t3YlIPIPTI6BWe3Sk6j4zCPL49M0NNbkTY4bESgBSkqIzfXHTngQyHKXXbn~gM7IQSIumumWVSxY8j3Ms2q813-NrE7J0D1EMRQokCmMQDTnaIzioYiDgIAnoBwurFdR6Ehl~VJfS55vWo50ajYaaGKPMQ__",
            price = 10_000,
            name = "PET보틀-단지(400ml) 레몬청",
        )
}
