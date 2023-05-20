package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

internal class ShoppingPresenterTest {

    private lateinit var presenter: ShoppingContract.Presenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productRepository: DomainProductRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, productRepository, recentProductRepository)
    }

    @Test
    internal fun 패치_올을_호출하면_제품과_최근_본_목록을_갱신한다() {
        // given
        every { productRepository.getPartially(any(), any()) } answers { listOf() }

        // when
        presenter.fetchAll()

        // then
        verify(exactly = 1) { view.updateProducts(any()) }
        verify(exactly = 1) { view.hideLoadMoreButton() }
    }

    @Test
    internal fun 제품_상세_내용을_조회한다() {
        // given
        val product = mockk<UiProduct>(relaxed = true)

        // when
        presenter.inquiryProductDetail(product)

        // then
        verify(exactly = 1) { view.updateRecentProducts(any()) }
        verify(exactly = 1) { view.navigateToProductDetail(any()) }
        verify(exactly = 1) { recentProductRepository.add(any()) }
    }

    @Test
    internal fun 최근_제품_목록을_갱신한다() {
        // given
        /* ... */

        // when
        presenter.fetchRecentProducts()

        // then
        verify(exactly = 1) { view.updateRecentProducts(any()) }
    }

    @Test
    internal fun 최근_상품_상세_내용을_조회한다() {
        // given
        val recentProduct = mockk<UiRecentProduct>(relaxed = true)

        // when
        presenter.inquiryRecentProductDetail(recentProduct)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(any()) }
        verify(exactly = 1) { recentProductRepository.add(any()) }
    }

    @Test
    internal fun 장바구니_목록을_조회한다() {
        // given
        /* ... */

        // when
        presenter.openBasket()

        // then
        verify(exactly = 1) { view.navigateToBasket() }
    }
}
