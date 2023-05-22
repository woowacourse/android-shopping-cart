package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.RecentProducts
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiPrice
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

internal class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingContract.Presenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter =
            ShoppingPresenter(view, productRepository, recentProductRepository, cartRepository)
    }

    @Test
    internal fun fetchAll_메서드를_호출하면_제품과_최근_본_목록을_갱신한다() {
        // given
        every { recentProductRepository.getPartially(any()) } returns RecentProducts(
            items = listOf(
                RecentProduct(1, Product(1, "상품", Price(1000), "상품 이미지"))
            )
        )

        // when
        presenter.fetchAll()

        // then
        verify(exactly = 1) { view.hideLoadMoreButton() }
        verify(exactly = 1) { view.updateRecentProducts(any()) }
    }

    @Test
    internal fun 제품_상세_내용을_조회한다() {
        // given
        val product = mockk<UiProduct>(relaxed = true)

        // when
        presenter.inquiryProductDetail(product)

        // then
        verify(exactly = 1) { view.updateRecentProducts(any()) }
        verify(exactly = 1) { view.navigateToProductDetail(product, any()) }
        verify(exactly = 1) { recentProductRepository.add(any()) }
    }

    @Test
    internal fun 최근_제품_목록을_갱신한다() {
        // given
        val recentProducts = mockk<RecentProducts>(relaxed = true)
        presenter = ShoppingPresenter(
            view,
            productRepository,
            recentProductRepository,
            cartRepository,
            10,
            recentProducts,
        )

        // when
        presenter.fetchRecentProducts()

        // then
        val expected = recentProducts.getItems().toUi()
        verify(exactly = 1) { view.updateRecentProducts(expected) }
    }

    @Test
    internal fun 최근_상품_상세_내용을_조회한다() {
        // given
        val recentProduct = mockk<UiRecentProduct>(relaxed = true)

        // when
        presenter.inquiryRecentProductDetail(recentProduct)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(any(), any()) }
        verify(exactly = 1) { recentProductRepository.add(any()) }
    }

    @Test
    internal fun 장바구니_목록을_조회한다() {
        // given
        /* ... */

        // when
        presenter.navigateToCart()

        // then
        verify(exactly = 1) { view.navigateToCart() }
    }

    @Test
    internal fun 장바구니_화면으로_이동한다() {
        // given
        /* ... */

        // when
        presenter.navigateToCart()

        // then
        verify(exactly = 1) { view.navigateToCart() }
    }

    @Test
    internal fun `제품_개수를_증가시킨다`() {
        // given
        val product = UiProduct(0, "제품", UiPrice(1000), "")
        val count = 3

        // when
        presenter.increaseCartCount(product, count)

        // then
        verify(exactly = 1) { cartRepository.increaseCartCount(product.toDomain(), count) }
        verify(exactly = 1) { view.updateCartBadge(any()) }
        verify(exactly = 1) { view.updateProducts(any()) }
    }
}
