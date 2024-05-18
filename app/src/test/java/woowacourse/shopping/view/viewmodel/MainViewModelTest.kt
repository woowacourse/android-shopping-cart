package woowacourse.shopping.view.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.MainViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        repository = MockProductRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `offset을_기준으로_상품_리스트를_요청하면_상품_목록을_정해진_개수만큼_반환해야_한다`() {
        val before = viewModel.products.getOrAwaitValue()
        assertThat(before.size).isEqualTo(0)

        viewModel.loadPagingProduct(3)

        val result = viewModel.products.getOrAwaitValue()
        assertThat(result.size).isEqualTo(3)
    }

    @Test
    fun `상품아이디로_상품을_요청하면_아이디와_일치하는_상품_목록을_반환해야_한다`() {
        val actual = viewModel.loadProductItem(0)
        val expected = (repository as MockProductRepository).products[0]

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `상품을_장바구니에_추가하면_장바구니에_상품이_추가되어야_한다`() {
        val newProduct =
            Product(
                id = 3L,
                imageUrl = "",
                price = 5_000,
                name = "아이스 아메리카노",
            )
        viewModel.addShoppingCartItem(newProduct).join()

        val actual = (repository as MockProductRepository).cartItems.last()
        val expected = CartItem(3L, newProduct)

        assertThat(actual).isEqualTo(expected)
    }
}
