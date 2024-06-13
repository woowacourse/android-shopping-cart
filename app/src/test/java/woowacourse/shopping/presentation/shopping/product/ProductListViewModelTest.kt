package woowacourse.shopping.presentation.shopping.product

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.shopping.toShoppingUiModel
import woowacourse.shopping.presentation.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class, MockKExtension::class)
class ProductListViewModelTest {
    @MockK
    lateinit var shoppingRepository: ShoppingRepository

    @MockK
    lateinit var cartRepository: CartRepository

    @InjectMockKs
    lateinit var productListViewModel: ProductListViewModel

    @Test
    @DisplayName("상품을 추가한 후,로드할 수 있는 상품이 있다면, 로드할 수 있다")
    fun `test`() {
        // given
        val product = product()
        val cartProduct = CartProduct(product, 1)
        val expectProducts = listOf(product.toShoppingUiModel(false), ShoppingUiModel.LoadMore)
        every { shoppingRepository.products(0, 20) } returns listOf(product)
        every { shoppingRepository.canLoadMoreProducts(1, 20) } returns true
        every { cartRepository.totalCartProducts() } returns listOf(cartProduct)
        // when
        productListViewModel.loadProducts()
        // then
        verify(exactly = 1) { shoppingRepository.products(0, 20) }
        verify(exactly = 1) { shoppingRepository.canLoadMoreProducts(1, 20) }
        productListViewModel.products.getOrAwaitValue() shouldBe expectProducts
    }

    @Test
    @DisplayName("상품을 추가한 후, 더 이상 로드할 수 있는 상품이 없을 때, 추가로 로드할 수 없다")
    fun test2() {
        // given
        val product = product()
        val cartProduct = CartProduct(product, 1)
        val expectProducts = listOf(product.toShoppingUiModel(false))
        every { shoppingRepository.products(0, 20) } returns listOf(product)
        every { shoppingRepository.canLoadMoreProducts(1, 20) } returns false
        every { cartRepository.totalCartProducts() } returns listOf(cartProduct)
        // when
        productListViewModel.loadProducts()
        // then
        verify(exactly = 1) { shoppingRepository.products(0, 20) }
        verify(exactly = 1) { shoppingRepository.canLoadMoreProducts(1, 20) }
        productListViewModel.products.getOrAwaitValue() shouldBe expectProducts
    }

    @Test
    @DisplayName("추가할 상품이 있다면, 더보기 버튼이 있어야 한다")
    fun `test3`() {
        // given
        val product = product()
        val loadMore = ShoppingUiModel.LoadMore
        val cartProduct = CartProduct(product, 1)
        every { shoppingRepository.products(0, 20) } returns listOf(product)
        every { shoppingRepository.canLoadMoreProducts(1, 20) } returns true
        every { cartRepository.totalCartProducts() } returns listOf(cartProduct)
        // when
        productListViewModel.loadProducts()
        // then
        val actualProducts = productListViewModel.products.getOrAwaitValue()
        verify(exactly = 1) { shoppingRepository.products(0, 20) }
        verify(exactly = 1) { shoppingRepository.canLoadMoreProducts(1, 20) }
        assertThat(actualProducts).contains(loadMore)
    }

    @Test
    @DisplayName("추가할 상품이 없다면, 더보기 버튼이 없어야 한다")
    fun `test4`() {
        // given
        val product = product()
        val loadMore = ShoppingUiModel.LoadMore
        val cartProduct = CartProduct(product, 1)
        every { shoppingRepository.products(0, 20) } returns listOf(product)
        every { shoppingRepository.canLoadMoreProducts(1, 20) } returns false
        every { cartRepository.totalCartProducts() } returns listOf(cartProduct)
        // when
        productListViewModel.loadProducts()
        // then
        val actualProducts = productListViewModel.products.getOrAwaitValue()
        verify(exactly = 1) { shoppingRepository.products(0, 20) }
        verify(exactly = 1) { shoppingRepository.canLoadMoreProducts(1, 20) }
        assertThat(actualProducts).doesNotContain(loadMore)
    }
}
