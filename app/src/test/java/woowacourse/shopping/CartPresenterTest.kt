package woowacourse.shopping

import com.example.domain.model.CartRepository
import com.example.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.cart.contract.presenter.CartPresenter
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `장바구니에 담긴 상품을 보여준다`() {
        // given
        val slot = slot<CartUIModel>()
        every { cartRepository.getSubList(any(), any()) } returns emptyList()
        every { view.setCarts(any(), capture(slot)) } answers { nothing }

        // when
        presenter.setUpCarts()

        // then
        assertEquals(slot.captured, CartUIModel(false, false, 1))
        verify(exactly = 1) { view.setCarts(any(), CartUIModel(false, false, 1)) }
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        every { cartRepository.remove(any()) } answers { nothing }
        // when
        presenter.removeItem(1)
        // then
        verify(exactly = 1) { cartRepository.remove(1) }
        verify(exactly = 1) { view.setCarts(any(), any()) }
    }

    @Test
    fun `다음 페이지 상품을 불러온다`() {
        // given
        val slot = slot<CartUIModel>()
        every { cartRepository.getSubList(any(), any()) } returns emptyList()
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.pageUp()
        // then
        assertEquals(slot.captured, CartUIModel(false, false, 1))
        verify(exactly = 1) { view.setCarts(any(), CartUIModel(false, false, 1)) }
    }

    @Test
    fun `이전 페이지 상품을 불러온다`() {
        // given
        val slot = slot<CartUIModel>()
        every { cartRepository.getSubList(any(), any()) } returns emptyList()
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.pageDown()
        // then
        assertEquals(slot.captured, CartUIModel(false, false, 1))
        verify(exactly = 1) { view.setCarts(any(), CartUIModel(false, false, 1)) }
    }

    @Test
    fun `상세 페이지로 이동한다`() {
        // given
        val slot = slot<CartUIModel>()
        every { cartRepository.getSubList(any(), any()) } returns emptyList()
        every { view.setCarts(any(), capture(slot)) } answers { nothing }

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel())

        // then
        verify(exactly = 1) { view.navigateToItemDetail(fakeProduct.toUIModel()) }
    }
}
