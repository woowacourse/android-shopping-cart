package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
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
        shoppingCartRepository = mockk()
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
        every { shoppingCartRepository.getAll() } returns productsInCart
        presenter = ShoppingCartPresenter(view, shoppingCartRepository)
    }

    @Test
    fun `장바구니 목록을 뷰에 세팅해준다`() {
        // given
        val slot = slot<List<ProductInCart>>()
        every { view.setShoppingCart(capture(slot)) } returns Unit

        // when
        presenter.fetchShoppingCart()
        val actual = slot.captured

        // then
        assertEquals(productsInCart, actual)
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

    @Test
    fun `장바구니에서 상품을 삭제하면 금액과 주문개수를 업데이트한다`() {
        every { shoppingCartRepository.deleteProductInCart(1) } returns true
        val countSlot = slot<Int>()
        val paymentSlot = slot<Int>()
        val expectedCount = 0
        val expectedPayment = 0
        every { view.updateOrder(capture(countSlot)) } returns Unit
        every { view.updatePayment(capture(paymentSlot)) } returns Unit

        // when
        presenter.deleteProductInCart(0)
        val actualCount = countSlot.captured
        val actualPayment = paymentSlot.captured

        // then
        assertEquals(expectedCount, actualCount)
        assertEquals(expectedPayment, actualPayment)
    }

    @Test
    fun `장바구니 수량 업데이트에 성공하면 뷰를 업데이트한다`() {
        // given
        val paymentSlot = slot<Int>()
        val countSlot = slot<Int>()
        val pagedCartSlot = slot<List<ProductInCart>>()
        every { view.setShoppingCart(capture(pagedCartSlot)) } returns Unit
        every { view.updatePayment(capture(paymentSlot)) } returns Unit
        every { view.updateOrder(capture(countSlot)) } returns Unit
        every { shoppingCartRepository.updateProductQuantity(1, 2) } returns WoowaResult.SUCCESS(1)
        val expectedCount = 2
        val expectedPayment = productsInCart.first().product.price * expectedCount
        val expectedPagedCart =
            productsInCart.map { ProductInCart(product = it.product, quantity = it.quantity + 1) }

        // when
        presenter.updateProductQuantity(0, Operator.INCREASE)
        val actualPayment = paymentSlot.captured
        val actualCount = countSlot.captured
        val actualPagedCart = pagedCartSlot.captured

        // then
        assertEquals(expectedCount, actualCount)
        assertEquals(expectedPayment, actualPayment)
        assertEquals(expectedPagedCart, actualPagedCart)
    }
}
