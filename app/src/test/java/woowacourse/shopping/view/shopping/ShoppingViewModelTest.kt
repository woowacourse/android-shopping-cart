package woowacourse.shopping.view.shopping

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.helper.FakeCartRepositoryImpl
import woowacourse.shopping.helper.InstantTaskExecutorExtension
import woowacourse.shopping.view.state.UIState

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private val shoppingRepository: ShoppingRepository = mockk()
    private val cartRepository: CartRepository = FakeCartRepositoryImpl()

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingViewModel(shoppingRepository, cartRepository)
    }

    @Test
    fun `초기 로딩 시 빈 데이터가 주어지면 UIState는 Empty로 설정된다`() {
        every { shoppingRepository.findProductsByPage() } returns emptyList()
        every { shoppingRepository.canLoadMore() } returns false

        viewModel.loadProducts()

        assertEquals(UIState.Empty, viewModel.shoppingUiState.value)
        assertEquals(false, viewModel.canLoadMore.value)
    }

    @Test
    fun `초기 로딩 시 데이터가 주어지면 UIState는 Success로 설정된다`() {
        val products =
            List(20) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        every { shoppingRepository.findProductsByPage() } returns products
        every { shoppingRepository.canLoadMore() } returns true

        viewModel.loadProducts()

        assertEquals(UIState.Success(products), viewModel.shoppingUiState.value)
        assertEquals(true, viewModel.canLoadMore.value)
    }

    @Test
    fun `로드된 데이터가 20개 이하이면 canLoadMore는 false로 설정된다`() {
        val products =
            List(19) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        every { shoppingRepository.findProductsByPage() } returns products
        every { shoppingRepository.canLoadMore() } returns false

        viewModel.loadProducts()

        assertEquals(false, viewModel.canLoadMore.value)
    }
}
