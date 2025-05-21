package woowacourse.shopping.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.cart.fixture.FakeCartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var fakeDataSource: FakeCartProductDataSource

    @BeforeEach
    fun setup() {
        fakeDataSource = FakeCartProductDataSource(generateMockProducts(11))
        viewModel = CartViewModel(fakeDataSource)
    }

    @Test
    fun `초기에는 첫 페이지 상품만 보여준다`() {
        val products = viewModel.cartProducts.value ?: emptyList()
        assertThat(products.size).isEqualTo(5)
        assertThat(viewModel.getPage()).isEqualTo(0)
    }

    @Test
    fun `상품 삭제 후 현재 페이지가 유효하지 않으면 이전 페이지로 이동한다`() {
        repeat(2) { viewModel.onNextPage() }
        assertThat(viewModel.getPage()).isEqualTo(2)

        val lastItem = viewModel.cartProducts.value!!.last()
        viewModel.onDeleteProduct(lastItem)

        assertThat(viewModel.getPage()).isEqualTo(1)
        assertThat(viewModel.cartProducts.value!!.size).isEqualTo(5)
    }

    @Test
    fun `다음 페이지 버튼은 다음 페이지가 있을 때만 활성화된다`() {
        assertThat(viewModel.isNextButtonEnabled()).isTrue

        repeat(2) { viewModel.onNextPage() }

        assertThat(viewModel.isNextButtonEnabled()).isFalse
    }

    @Test
    fun `이전 페이지 버튼은 첫 페이지가 아닐 때만 활성화된다`() {
        assertThat(viewModel.isPrevButtonEnabled()).isFalse

        viewModel.onNextPage()
        assertThat(viewModel.isPrevButtonEnabled()).isTrue
    }

    private fun generateMockProducts(count: Int): List<ProductUiModel> =
        (1..count).map {
            ProductUiModel(
                name = "상품$it",
                imageUrl = "",
                price = it * 1000,
            )
        }
}
