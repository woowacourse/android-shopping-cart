package woowacourse.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shopping.ShoppingContract
import woowacourse.shopping.shopping.ShoppingPresenter
import woowacourse.shopping.util.toUiModel

class ShoppingPresenterTest {

    lateinit var presenter: ShoppingContract.Presenter
    lateinit var view: ShoppingContract.View
    lateinit var repository: ShoppingRepository
    lateinit var products: List<Product>

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        view = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, repository)
        products = listOf(
            Product(name = "아메리카노"),
            Product(name = "카페라떼"),
        )
    }

    @Test
    fun `저장소로부터 상품 목록을 받아와서 뷰를 초기화한다`() {
        // given
        val slot = slot<List<ProductUiModel>>()
        every {
            view.setUpShoppingView(
                products = capture(slot),
                any(),
                any(),
            )
        } just Runs
        every { repository.selectRecentViewedProducts() } returns products
        every { repository.selectProducts(any(), any()) } returns products

        // when
        presenter.loadProducts()

        // then
        val actual = slot.captured
        val expected = products.map { it.toUiModel() }
        assertEquals(expected, actual)
        verify {
            view.setUpShoppingView(
                products = actual,
                any(),
                any(),
            )
        }
    }

    @Test
    fun `저장소로부터 최근 본 상품을 받아와서 뷰를 초기화한다`() {
        // given
        val slot = slot<List<ProductUiModel>>()
        every {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = capture(slot),
                any(),
            )
        } just Runs
        every { repository.selectRecentViewedProducts() } returns products

        // when
        presenter.loadProducts()

        // then
        val actual = slot.captured
        val expected = products.map { it.toUiModel() }
        assertEquals(expected, actual)
        verify {
            view.setUpShoppingView(
                any(),
                recentViewedProducts = actual,
                any(),
            )
        }
    }

    @Test
    fun `저장소에서 추가적인 상품 목록을 불러와서 뷰를 초기화한다`() {
        // given
        every { repository.selectProducts(any(), any()) } returns products

        // when
        presenter.readMoreShoppingProducts()

        // then
        verify { view.refreshShoppingProductsView(products.map { it.toUiModel() }) }
    }

    @Test
    fun `선택한 상품을 최근 본 상품 저장소에 저장한다`() {
        // when
        presenter.addToRecentViewedProduct(1)

        // then
        verify { repository.insertToRecentViewedProducts(1) }
    }

    @Test
    fun `저장소로부터 최근 본 상품을 받아와서 뷰를 갱신한다`() {
        // given
        every { repository.selectRecentViewedProducts() } returns products

        // when
        presenter.updateRecentViewedProducts()

        // then
        val expected = products.map { it.toUiModel() }
        verify { view.refreshRecentViewedProductsView(expected) }
    }
}
