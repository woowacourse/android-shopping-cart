package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

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
        productRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository, productRepository)
    }

    @Test
    fun `장바구니에 담긴 상품을 보여준다`() {
        // given
        val slot = slot<PageUIModel>()
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { view.setCarts(any(), capture(slot)) } answers { nothing }

        // when
        presenter.setUpCarts()

        // then
        assertEquals(slot.captured, PageUIModel(false, false, 1))
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(false, false, 1)) }
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
        val slot = slot<PageUIModel>()
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.moveToPageNext()
        // then
        assertEquals(slot.captured, PageUIModel(false, false, 2))
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(false, false, 2)) }
    }

    @Test
    fun `이전 페이지 상품을 불러온다`() {
        // given
        val slot = slot<PageUIModel>()
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { view.setCarts(any(), capture(slot)) } answers { nothing }
        // when
        presenter.moveToPagePrev()
        // then
        assertEquals(slot.captured, PageUIModel(false, false, 0))
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(false, false, 0)) }
    }

    @Test
    fun `상세 페이지로 이동한다`() {
        // given
        val slot = slot<PageUIModel>()
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { productRepository.findById(any()) } returns fakeProduct
        every { view.setCarts(any(), capture(slot)) } answers { nothing }

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToItemDetail(fakeProduct.toUIModel()) }
    }
}
