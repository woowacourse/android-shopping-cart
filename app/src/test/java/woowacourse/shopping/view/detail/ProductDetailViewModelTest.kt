package woowacourse.shopping.view.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ProductRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        repository = MockProductRepository()
        viewModel = ProductDetailViewModel(repository, 0L)
    }

    @Test
    fun `선택된_상품_데이터를_로드한다`() {
        viewModel = ProductDetailViewModel(repository, 0L)

        val actual = viewModel.product.getOrAwaitValue()
        val expected = (repository as MockProductRepository).products.first()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택한_상품을_장바구니에_추가하면_장바구니에_상품이_추가되어야_한다`() {
        viewModel.addShoppingCartItem()

        val actual = (repository as MockProductRepository).cartItems.last()
        val saved = viewModel.product.getOrAwaitValue()
        val expected = CartItem(0L, saved)

        assertThat(actual).isEqualTo(expected)
    }
}
