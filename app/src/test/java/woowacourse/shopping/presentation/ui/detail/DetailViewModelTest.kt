package woowacourse.shopping.presentation.ui.detail

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testProduct0
import testProductWithQuantity0
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class DetailViewModelTest {
    @RelaxedMockK
    private lateinit var cartRepository: CartRepository

    @RelaxedMockK
    private lateinit var productRepository: ShoppingItemsRepository

    @RelaxedMockK
    private lateinit var recentlyViewedProductsRepository: RecentlyViewedProductsRepository

    @InjectMockKs
    private lateinit var detailViewModel: DetailViewModel

    private val dummyProductId = 0L

    @BeforeEach
    fun setUp() {
        every { productRepository.productWithQuantityItem(dummyProductId) } returns Result.success(testProductWithQuantity0)
        every { recentlyViewedProductsRepository.getRecentlyViewedProducts(1) } returns Result.success(listOf())
        detailViewModel = DetailViewModel(cartRepository, productRepository, recentlyViewedProductsRepository, dummyProductId)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        // when
        val actual = detailViewModel.productWithQuantity.getOrAwaitValue()

        // then
        assertThat(actual.product.id).isEqualTo(testProduct0.id)
        assertThat(actual.product.name).isEqualTo(testProduct0.name)
        assertThat(actual.product.price).isEqualTo(testProduct0.price)
        assertThat(actual.product.imageUrl).isEqualTo(testProduct0.imageUrl)
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        // given
        every { cartRepository.insert(any()) } answers {}

        // when
        detailViewModel.onAddToCartClicked(testProduct0.id)

        // then
        val actual = detailViewModel.addToCart.getOrAwaitValue()
        assertThat(actual).isEqualTo(testProduct0.id)
    }
}
