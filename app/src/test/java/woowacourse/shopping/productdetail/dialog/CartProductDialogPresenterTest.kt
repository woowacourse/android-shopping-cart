package woowacourse.shopping.productdetail.dialog

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.repository.CartRepository

class CartProductDialogPresenterTest {
    private lateinit var presenter: CartProductDialogPresenter
    private lateinit var view: CartProductDialogContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = CartProductDialogPresenter(
            view,
            productModel = mockk(relaxed = true),
            cartRepository
        )
    }

    @Test
    fun 프레젠터를_생성하면_뷰에_카트_상품_수량이_셋업된다() {
        // given

        // when

        // then
        verify { view.setupCartProductAmount(any()) }
    }
}
