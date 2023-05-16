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
    fun `장바구니에 추가하면 저장소에 상품정보를 저장하고 장바구니 화면으로 넘어간다`() {
        // given
        val slot = slot<Int>()
        every { repository.insertToShoppingCart(capture(slot)) } just Runs
        every { view.navigateToShoppingCartView() } just Runs

        // when
        presenter.addToShoppingCart()

        // then
        val actual = slot.captured
        val expected = Product(1)
        assertEquals(expected.id, actual)
        verify { repository.insertToShoppingCart(actual) }
    }
}
