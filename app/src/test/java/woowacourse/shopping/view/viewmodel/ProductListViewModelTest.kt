package woowacourse.shopping.view.viewmodel

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockProductRepository
import woowacourse.shopping.MockRecentlyProductRepository
import woowacourse.shopping.MockShoppingCartRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.view.products.ProductListViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        val productRepository = MockProductRepository()
        val shoppingCartRepository = MockShoppingCartRepository()
        val recentlyProductRepository = MockRecentlyProductRepository()
        viewModel =
            ProductListViewModel(
                productRepository = productRepository,
                shoppingCartRepository = shoppingCartRepository,
                recentlyProductRepository = recentlyProductRepository,
            )
    }

    @Test
    fun `offset을_기준으로_상품_리스트를_요청하면_상품_목록을_정해진_개수만큼_반환해야_한다`() {
        // given
        val before = viewModel.products.getOrAwaitValue()
        Assertions.assertThat(before.size).isEqualTo(0)

        // when
        viewModel.loadPagingProduct()

        // then
        val result = viewModel.products.getOrAwaitValue()
        Assertions.assertThat(result.size).isEqualTo(3)
    }
}
