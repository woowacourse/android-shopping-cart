package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.FakeCartRepository
import woowacourse.shopping.database.FakeProductRepository

class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter =
            CartPresenter(view, FakeCartRepository)
    }

    @Test
    fun 장바구니에_담은_상품을_삭제하면_장바구니_목록에_해당_상품이_삭제된다() {
        every { view.setCartItems(any()) } answers { }

        presenter.deleteCartItem(2)
        val product = FakeProductRepository.findById(2)

        Assert.assertEquals(false, FakeCartRepository.findAll().contains(product))
    }
}
