package woowacourse.shopping.presentation.ui.shopping

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private val repository: ShoppingItemsRepository = mockk()

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingViewModel(repository)
    }

    @Test
    fun `10개 이하의 데이터가 존재할때는 isLoadMoreButtonVisible의 값에는 항상 false가 저장된다`() {
        every { repository.findProductsByPage() } returns
            List(8) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        every { repository.canLoadMore() } returns false

        viewModel.loadProducts()
        viewModel.updateLoadMoreButtonVisibility(true)

        assertEquals(false, viewModel.isLoadMoreButtonVisible.value)
    }

    @Test
    fun `10개 초과의 데이터가 존재하고 isVisible이 true라면 isLoadMoreButtonVisible의 값은 true가 저장된다`() {
        every { repository.findProductsByPage() } returns
            List(11) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        every { repository.canLoadMore() } returns true

        viewModel.loadProducts()
        viewModel.updateLoadMoreButtonVisibility(true)

        assertEquals(true, viewModel.isLoadMoreButtonVisible.value)
    }

    @Test
    fun `10개 초과의 데이터가 존재하고 isVisible이 false라면 isLoadMoreButtonVisible의 값은 false 저장된다`() {
        every { repository.findProductsByPage() } returns
            List(11) {
                Product(
                    id = it.toLong(),
                    name = "Product $it",
                    price = 1000,
                    imageUrl = "URL $it",
                )
            }
        every { repository.canLoadMore() } returns true

        viewModel.loadProducts()
        viewModel.updateLoadMoreButtonVisibility(false)

        assertEquals(false, viewModel.isLoadMoreButtonVisible.value)
    }
}
