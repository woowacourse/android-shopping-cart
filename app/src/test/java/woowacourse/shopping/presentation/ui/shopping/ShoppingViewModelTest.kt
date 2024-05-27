package woowacourse.shopping.presentation.ui.shopping

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var testCartRepository: CartRepository
    private var testRecentProductRepository: RecentProductRepository = mockk()
    private val repository: ShoppingItemsRepository = mockk()

    @BeforeEach
    fun setUp() {
        val products =
            listOf(
                Product.of(name = "Product 1", price = 1000, imageUrl = "URL 1"),
                Product.of(name = "Product 2", price = 1000, imageUrl = "URL 2"),
                Product.of(name = "Product 3", price = 1000, imageUrl = "URL 3"),
                Product.of(name = "Product 4", price = 1000, imageUrl = "URL 4"),
                Product.of(name = "Product 5", price = 1000, imageUrl = "URL 5"),
                Product.of(name = "Product 6", price = 1000, imageUrl = "URL 6"),
                Product.of(name = "Product 7", price = 1000, imageUrl = "URL 7"),
                Product.of(name = "Product 8", price = 2000, imageUrl = "URL 8"),
                Product.of(name = "Product 9", price = 2000, imageUrl = "URL 9"),
                Product.of(name = "Product 10", price = 2000, imageUrl = "URL 10"),
            )

        every { repository.fetchProductsSize() } returns 100
        every { repository.fetchProductsWithIndex(any(), any()) } returns products
        every { testRecentProductRepository.loadLatestList() } returns listOf<RecentProduct>()

        testCartRepository = FakeCartRepositoryImpl()

        viewModel = ShoppingViewModel(repository, testCartRepository, testRecentProductRepository)
    }

    @Test
    fun `초기페이지의 상품 목록을 가져올 수 있다`() {
        assertEquals(10, viewModel.products.value?.size)
    }

    @Test
    fun `1번째 페이지를 불러왔을 때, 다음 페이지의를 요청하면 2번째 페이지의 상품 목록을 가져온다`() {
        viewModel.loadNextProducts()
        assertEquals(20, viewModel.products.value?.size)
    }

    @Test
    fun `더보기 버튼을 노출할 수 있다`() {
        viewModel.showLoadMore()

        assertEquals(true, viewModel.showLoadMore.value)
    }

    @Test
    fun `더보기 버튼을 숨길 수 있다`() {
        viewModel.hideLoadMore()

        assertEquals(false, viewModel.showLoadMore.value)
    }
}
