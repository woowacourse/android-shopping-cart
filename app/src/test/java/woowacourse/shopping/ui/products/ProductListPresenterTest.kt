package woowacourse.shopping.ui.products

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState
import java.time.LocalDateTime

class ProductListPresenterTest {

    private lateinit var view: ProductListContract.View
    private lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var sut: ProductListPresenter
    private val fakeProduct = Product(1L, "www.naver.com", "네이버", 20_000)
    private val fakeRecentlyViewedProduct =
        RecentlyViewedProduct(fakeProduct, LocalDateTime.MAX).apply { id = 1 }

    @BeforeEach
    fun setUp() {
        view = mockk()
        recentlyViewedProductRepository = mockk()
        productRepository = mockk()
        sut = ProductListPresenter(view, recentlyViewedProductRepository, productRepository)
    }

    @Test
    fun `현재 페이지를 요청하면 현재 페이지를 반환한다`() {
        val actual = sut.getCurrentPage()

        assertThat(actual).isZero
    }

    @Test
    fun `현재 페이지를 회복하면 현재 페이지가 변하고 그에 맞는 상품들과 페이지 관련 UI를 보여준다`() {
        every { productRepository.findAll(any(), any()) } returns listOf(fakeProduct)
        every { productRepository.countAll() } returns 1
        every { view.addProducts(listOf(fakeProduct).map(ProductUIState::from)) } just runs
        every { view.setCanLoadMore(false) } just runs

        sut.restoreCurrentPage(1)

        verify { view.addProducts(listOf(fakeProduct).map(ProductUIState::from)) }
        verify { view.setCanLoadMore(false) }
    }

    @Test
    fun `최근 본 상품들을 로드하면 최근 본 상품들을 보여준다`() {
        every { recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc() } returns listOf(
            fakeRecentlyViewedProduct
        )
        every {
            view.setRecentlyViewedProducts(
                listOf(fakeRecentlyViewedProduct).map(RecentlyViewedProductUIState::from)
            )
        } just runs

        sut.onLoadRecentlyViewedProducts()

        verify {
            view.setRecentlyViewedProducts(
                listOf(fakeRecentlyViewedProduct).map(RecentlyViewedProductUIState::from)
            )
        }
    }

    @Test
    fun `다음 페이지를 로드하면 현재 페이지가 1 증가하고 그에 맞는 상품들과 페이지 관련 UI를 보여준다`() {
        every { productRepository.findAll(any(), any()) } returns listOf(fakeProduct)
        every { productRepository.countAll() } returns 1
        every { view.addProducts(listOf(fakeProduct).map(ProductUIState::from)) } just runs
        every { view.setCanLoadMore(false) } just runs

        sut.onLoadProductsNextPage()

        assertThat(sut.getCurrentPage()).isEqualTo(1)
        verify { view.addProducts(listOf(fakeProduct).map(ProductUIState::from)) }
        verify { view.setCanLoadMore(false) }
    }
}