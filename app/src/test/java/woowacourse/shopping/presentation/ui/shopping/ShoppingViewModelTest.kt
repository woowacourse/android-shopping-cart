package woowacourse.shopping.presentation.ui.shopping

import io.mockk.*
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
        every { repository.getAllProducts() } returns
            listOf(
                Product.of(name = "Product 1", price = 1000, imageUrl = "URL 1"),
                Product.of(name = "Product 2", price = 2000, imageUrl = "URL 2"),
            )

        viewModel = ShoppingViewModel(repository)
    }

    @Test
    fun `loadProducts가 호출됐을 때 products LiveData가 업데이트된다`() {
        // viewModel.loadProducts()
        // assertEquals(2, viewModel.products.value?.size)
    }

    @Test
    fun `updateVisibility가 호출됐을 때 visibility LiveData가 업데이트된다_1`() {
        viewModel.showLoadMoreBtn()

        assertEquals(true, viewModel.isBtnVisibleCondition.value)
    }

    @Test
    fun `updateVisibility가 호출됐을 때 visibility LiveData가 업데이트된다_2`() {
        viewModel.hideLoadMoreBtn()

        assertEquals(false, viewModel.isBtnVisibleCondition.value)
    }
}
