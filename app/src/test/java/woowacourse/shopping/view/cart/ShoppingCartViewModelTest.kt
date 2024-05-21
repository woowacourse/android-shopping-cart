package woowacourse.shopping.view.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        repository = MockProductRepository()
    }

    @Test
    fun `장바구니에_담긴_아이템이_한_화면에_담을_수_있는_최대_개수_이하라면_모든_페이지_컨트롤이_비활성화된다`() {
        repeat(2) {
            repository.addCartItem(Product(it))
        }

        viewModel = ShoppingCartViewModel(repository)

        assertThat(viewModel.isPrevButtonActivated.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.isNextButtonActivated.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun `장바구니에_담긴_아이템이_한_화면에_담을_수_있는_최대_개수_초과라면_다음_페이지_컨트롤이_활성화된다`() {
        repeat(5) {
            repository.addCartItem(Product(it))
        }

        viewModel = ShoppingCartViewModel(repository)

        assertThat(viewModel.isPrevButtonActivated.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.isNextButtonActivated.getOrAwaitValue()).isEqualTo(true)
    }
}

private fun Product(id: Int): Product {
    return Product(id.toLong(), "product $id", 1_000, "")
}
