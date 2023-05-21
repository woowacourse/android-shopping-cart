package woowacourse.shopping.ui.cart.contract.presenter

import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.contract.CartContract

internal class CartPresenterTest {
    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg",
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
        Assert.assertEquals(slot.captured, CartUIModel(false, false, 1))
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
        every { cartRepository.getAll() } returns listOf(CartProduct(fakeProduct, 1, true))
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.pageUp()
        // then
        Assert.assertEquals(slot.captured, CartUIModel(false, true, 2))
        verify(exactly = 1) { view.setCarts(any(), CartUIModel(false, true, 2)) }
    }

    @Test
    fun `이전 페이지 상품을 불러온다`() {
        // given
        val slot = slot<CartUIModel>()
        every { cartRepository.getSubList(any(), any()) } returns emptyList()
        every { cartRepository.getAll() } returns listOf(CartProduct(fakeProduct, 1, true))
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.pageDown()
        // then
        Assert.assertEquals(slot.captured, CartUIModel(true, false, 0))
        verify { view.setCarts(any(), CartUIModel(true, false, 0)) }
    }

    @Test
    fun `상세 페이지로 이동한다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { cartRepository.getFindById(any()) } returns CartProduct(fakeProduct, 1, true)
        every { view.navigateToItemDetail(capture(slot)) } answers { nothing }
        // when
        presenter.navigateToItemDetail(fakeProduct.id)
        // then
        assertEquals(slot.captured, fakeProduct.toUIModel())
        verify(exactly = 1) { view.navigateToItemDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `offset 상태를 저장할 수 있다`() {
        // given
        val slot = slot<Int>()
        every { cartRepository.getSubList(any(), capture(slot)) } returns emptyList()
        every { view.setCarts(any(), any()) } answers { nothing }
        // when
        // then
        assertEquals(slot.captured, 1)
        verify(exactly = 1) { cartRepository.getSubList(any(), 1) }
    }
}
