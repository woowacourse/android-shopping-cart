package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: CartContract.Presenter

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk()
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `장바구니의 아이템 클릭 리스너를 정의한다`() {
        // given
        val cartProductUIModel = CartProductUIModel.dummy
        val position = 1
        val slotCartProductUIModel = slot<CartProductUIModel>()
        val slotPosition = slot<Int>()
        every { cartRepository.remove(cartProductUIModel) } just runs
        every { view.removeAdapterData(cartProductUIModel, position) } just runs

        // when
        presenter.setOnClickRemove()(cartProductUIModel, position)

        // then
        verify { view.removeAdapterData(capture(slotCartProductUIModel), capture(slotPosition)) }
        assertEquals(slotCartProductUIModel.captured, cartProductUIModel)
        assertEquals(slotPosition.captured, position)
    }
}
