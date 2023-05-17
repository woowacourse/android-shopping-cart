package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartContract
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartPresenter

class ShoppingCartPresenterTest {
    private lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var view: ShoppingCartContract.View
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productsInCart: List<ProductInCart>

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        shoppingCartRepository = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(view, shoppingCartRepository)
        productsInCart = listOf(
            ProductInCart(
                product = Product(
                    id = 1,
                    name = "BMW i8",
                    price = 13000,
                    itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png",
                ),
                quantity = 1,
            ),
        )
    }

    @Test
    fun `장바구니 목록을 가져온다`() {
        // given
        val slot = slot<List<ProductInCart>>()
        every { shoppingCartRepository.getShoppingCart(5, 1) } returns productsInCart
        every { view.setShoppingCart(capture(slot)) } returns Unit

        // when
        presenter.getShoppingCart()
        val actual = slot.captured

        // then
        assertEquals(productsInCart, actual)
        verify(exactly = 1) { shoppingCartRepository.getShoppingCart(5, 1) }
        verify(exactly = 1) { view.setShoppingCart(productsInCart) }
    }

    @Test
    fun `페이지 넘버를 세팅한다`() {
        // given
        val slot = slot<Int>()
        every { view.setPage(capture(slot)) } returns Unit

        // when
        presenter.setPageNumber()
        val actual = slot.captured

        // then
        assertEquals(1, actual)
    }

    @Test
    fun `페이지 이동 가능성을 체크한다`() {
        // given
        val nextEnableSlot = slot<Boolean>()
        val previousEnableSlot = slot<Boolean>()
        every { shoppingCartRepository.getShoppingCartSize() } returns 20
        every {
            view.setPageButtonEnable(
                capture(previousEnableSlot),
                capture(nextEnableSlot),
            )
        } returns Unit

        // when
        presenter.checkPageMovement()
        val actualNextEnable = nextEnableSlot.captured
        val actualPreviousEnable = previousEnableSlot.captured

        // then
        assertEquals(true, actualNextEnable)
        assertEquals(false, actualPreviousEnable)
    }
}
