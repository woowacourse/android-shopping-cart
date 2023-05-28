package woowacourse.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter
import woowacourse.shopping.util.toUiModel

class ProductDetailPresenterTest {

    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var repository: ShoppingRepository

    @Before
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = ProductDetailPresenter(view, Product(id = 1).toUiModel(), repository)
    }

    @Test
    fun `화면을 초기화한다`() {
        // given
        val products = listOf(
            Product(0),
            Product(1, "아메리카노"),
        )
        every { repository.selectRecentViewedProducts() } returns products
        every { view.setUpRecentViewedProduct(any()) } just Runs

        // when
        presenter.setUpView()

        // then
        val expected = ProductUiModel(1, "아메리카노")
        assertEquals(expected, products[1].toUiModel())
        verify { view.setUpRecentViewedProduct(expected) }
    }

    @Test
    fun `장바구니 담기 버튼을 눌렀을 때 수량 선택 창을 띄운다`() {
        // given
        every { view.showCountProductView() } just Runs

        // when
        presenter.onClickShoppingCartBtn()

        // then
        verify { view.showCountProductView() }
    }

    @Test
    fun `수량을 더한다면 count가 1이 된다`() {
        // when
        presenter.changeCount(isAdd = true)

        // then
        assertEquals(1, presenter.count)
    }

    @Test
    fun `count가 2인 상태에서 수량을 뺀다면 count가 1이 된다`() {
        // given
        presenter.changeCount(isAdd = true)
        presenter.changeCount(isAdd = true)

        // when
        presenter.changeCount(isAdd = false)

        // then
        assertEquals(1, presenter.count)
    }

    @Test
    fun `장바구니에 추가하면 저장소에 상품정보를 저장하고 장바구니 화면으로 넘어간다`() {
        // given
        val slot = slot<Int>()
        every { repository.insertToShoppingCart(capture(slot), any(), any()) } just Runs
        every { view.navigateToShoppingCartView() } just Runs

        // when
        presenter.addToShoppingCart()

        // then
        val actual = slot.captured
        val expected = Product(1)
        assertEquals(expected.id, actual)
        verify { repository.insertToShoppingCart(actual, any(), any()) }
    }
}
