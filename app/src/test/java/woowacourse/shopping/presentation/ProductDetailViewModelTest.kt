package woowacourse.shopping.presentation

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.fixture.ProductsFixture
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: FakeProductRepository
    private lateinit var recentProductRepository: FakeRecentProductRepository
    private lateinit var viewModel: ProductDetailViewModel

    @BeforeEach
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        productRepository = FakeProductRepository()
        recentProductRepository = FakeRecentProductRepository()
        viewModel =
            ProductDetailViewModel(
                cartRepository,
                productRepository,
                recentProductRepository,
            )
    }

    @Test
    fun 상품_정보를_불러온다() {
        viewModel.fetchData(ProductsFixture.dummyProduct.productId)

        val product = viewModel.product.getOrAwaitValue()
        assertThat(product).isEqualTo(ProductsFixture.dummyProduct)
    }

    @Test
    fun `현재_상품이_가장_최근_본_상품이면_isRecentPdocut가_true이다`() {
        val productId = 0L
        recentProductRepository.recentProduct =
            ProductsFixture.dummyProduct.copy(productId = productId)

        viewModel.fetchData(productId)

        val result = viewModel.isRecentProduct.getOrAwaitValue()
        assertThat(result).isTrue()
    }

    @Test
    fun `현재_상품이_가장_최근_본_상품이_아니면_isRecentPdocut가_false이다`() {
        val productId = 1L
        recentProductRepository.recentProduct =
            ProductsFixture.dummyProduct.copy(productId = productId)

        viewModel.fetchData(productId)

        val result = viewModel.isRecentProduct.getOrAwaitValue()
        assertThat(result).isFalse()
    }

    @Test
    fun `상품_개수_증가_버튼을_누르면_개수가_증가한다`() {
        // 상품 초기값 1
        viewModel.fetchData(1L)
        viewModel.onClickPlus(1L)

        val result = viewModel.productCount.getOrAwaitValue()
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `상품_개수_증가_버튼을_누르면_개수가_감소한다`() {
        // 상품 초기값 1
        viewModel.fetchData(1L)
        // 증가시켜서 개수 2
        viewModel.onClickPlus(1L)
        viewModel.onClickMinus(1L)

        val result = viewModel.productCount.getOrAwaitValue()
        assertThat(result).isEqualTo(1)
    }
}
