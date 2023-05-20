package woowacourse.shopping.ui.basket

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.model.UiBasketProduct
import woowacourse.shopping.ui.model.UiPrice
import woowacourse.shopping.ui.model.UiProduct

class BasketPresenterTest() {
    private lateinit var view: BasketContract.View
    private lateinit var basketRepository: BasketRepository
    private lateinit var presenter: BasketPresenter
    private val pagingSize = 5

    @Before
    fun initBasketPresenter() {
        view = mockk(relaxed = true)
        basketRepository = mockk(relaxed = true)
        presenter = BasketPresenter(view, basketRepository)
    }

    @Test
    fun `장바구니 데이터의 전페이지를 조회하면 화면과 네비게이션 상태가 업데이트 된다`() {
        // given
        every {
            basketRepository.getPartially(
                size = any(),
                standard = any(),
                isNext = false,
                includeStandard = false
            )
        } returns List(pagingSize) { BasketProduct(it, Product(it, "더미", Price(1000), "url")) }

        // when
        presenter.fetchPreviousBasketProducts(
            List(pagingSize) {
                UiBasketProduct(
                    it,
                    UiProduct(
                        it,
                        "더미",
                        UiPrice(1000),
                        "url"
                    )
                )
            }
        )

        // then
        val updateBasketProductsCount = slot<List<UiBasketProduct>>()
        verify(exactly = 1) { view.updateBasketProducts(capture(updateBasketProductsCount)) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        assertEquals(updateBasketProductsCount.captured.size, pagingSize)
    }

    @Test
    fun `장바구니 데이터의 다음 페이지를 조회하면 화면과 네비게이션 상태가 업데이트 된다`() {
        // given
        every {
            basketRepository.getPartially(
                size = any(),
                standard = any(),
                isNext = true,
                includeStandard = any()
            )
        } returns List(pagingSize) { BasketProduct(it, Product(it, "더미", Price(1000), "url")) }

        // when
        presenter.fetchBasketProducts(
            6,
            true
        )

        // then
        val updateBasketProductsCount = slot<List<UiBasketProduct>>()
        verify(exactly = 1) { view.updateBasketProducts(capture(updateBasketProductsCount)) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        assertEquals(updateBasketProductsCount.captured.size, pagingSize)
    }

    @Test
    fun `장바구니 아이템을 삭제하면 최신화된 데이터가 화면에 업데이트 된다`() {
        // given
        every { basketRepository.remove(any()) } just runs
        every {
            basketRepository.getPartially(
                size = any(),
                standard = any(),
                isNext = true,
                includeStandard = true
            )
        } returns List(pagingSize) { BasketProduct(it, Product(it, "더미", Price(1000), "url")) }

        // when
        presenter.deleteBasketProduct(
            product = UiBasketProduct(
                1,
                UiProduct(1, "더미", UiPrice(1000), "url")
            ),
            List(pagingSize) {
                UiBasketProduct(
                    it,
                    UiProduct(
                        it,
                        "더미",
                        UiPrice(1000),
                        "url"
                    )
                )
            }
        )

        // then
        val updateBasketProductsCount = slot<List<UiBasketProduct>>()
        verify(exactly = 1) { view.updateBasketProducts(capture(updateBasketProductsCount)) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        assertEquals(updateBasketProductsCount.captured.size, pagingSize)
    }

    @Test
    fun `현재 페이지의 업데이트가 있을경우 업데이트된 페이지가 화면에 표시된다`() {
        // given
        every { view.updateCurrentPage(any()) } just runs

        // when
        presenter.updateCurrentPage(true)
        presenter.updateCurrentPage(false)

        // then
        verify(exactly = 2) { view.updateCurrentPage(any()) }
    }
}
