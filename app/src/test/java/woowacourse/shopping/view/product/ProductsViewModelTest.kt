package woowacourse.shopping.view.product

import io.mockk.Awaits
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.product.repository.ProductsRepository

class ProductsViewModelTest {
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: ProductsViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        viewModel = ProductsViewModel(repository)
    }

    @Test
    fun `등록된 상품들을 가져올 수 있다`() {
        // given
        every { repository.load(any(), any()) } just Awaits
        every { repository.loadable } just Awaits

        // when
        viewModel.updateProducts()

        // then
        verify { repository.load(any(), any()) }
    }
}
