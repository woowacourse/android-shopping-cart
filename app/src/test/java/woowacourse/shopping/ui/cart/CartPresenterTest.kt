package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProduct
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

    private val fakeCartProducts = CartProducts(
        List(10) {
            CartProduct(
                1,
                "aa",
                1,
                true,
                12000,
                "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            )
        }
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository, productRepository)
    }

    @Test
    fun `장바구니에 담긴 상품을 보여주고 금액과 선택된 개수를 설정한다`() {
        // given
        val fakeCartProducts = CartProducts(listOf())
        every { cartRepository.getPage(any(), any()) } returns fakeCartProducts
        every { cartRepository.hasNextPage(any(), any()) } returns true
        every { cartRepository.hasPrevPage(any(), any()) } returns true
        every { view.setCarts(any(), any()) } answers { nothing }

        // when
        presenter.setUpCarts()

        // then
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(true, true, 1)) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
    }

    @Test
    fun `다음 페이지 상품을 불러온다`() {
        // given
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { cartRepository.hasNextPage(any(), any()) } returns true
        every { cartRepository.hasPrevPage(any(), any()) } returns true
        every { view.setCarts(any(), any()) } answers { nothing }

        // when
        presenter.moveToPageNext()

        // then
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(true, true, 2)) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
    }

    @Test
    fun `이전 페이지 상품을 불러온다`() {
        // given
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { cartRepository.hasNextPage(any(), any()) } returns true
        every { cartRepository.hasPrevPage(any(), any()) } returns true
        every { view.setCarts(any(), any()) } answers { nothing }
        // when
        presenter.moveToPagePrev()
        // then
        verify(exactly = 1) { view.setCarts(any(), PageUIModel(true, true, 0)) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
    }

    @Test
    fun `현재 페이지의 모든 상품을 선택한다`() {
        // given
        every { cartRepository.getPage(any(), any()) } returns fakeCartProducts
        every { cartRepository.hasNextPage(any(), any()) } returns true
        every { cartRepository.hasPrevPage(any(), any()) } returns true
        every { view.setCarts(any(), any()) } answers { nothing }
        // when
        presenter.setProductsCheck(true)

        // then
        verify(exactly = 1) { view.setCarts(any(), any()) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
        verify(exactly = 10) { cartRepository.updateChecked(any(), true) }
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        every { cartRepository.remove(any()) } answers { nothing }

        // when
        presenter.removeProduct(1)

        // then
        verify(exactly = 1) { view.setCarts(any(), any()) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
        verify(exactly = 1) { cartRepository.remove(1) }
    }

    @Test
    fun `상세 페이지로 이동한다`() {
        // given
        every { cartRepository.getPage(any(), any()) } returns CartProducts(listOf())
        every { productRepository.findById(any()) } returns fakeProduct
        every { view.setCarts(any(), any()) } answers { nothing }

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToItemDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `현재 페이지 인덱스를 반환한다`() {
        // given
        val index = 5

        // when
        val presenter = CartPresenter(view, cartRepository, productRepository, index)

        // then
        assertEquals(presenter.getPageIndex(), index)
    }

    @Test
    fun `아이템의 개수를 변경한다`() {
        // given

        // when
        presenter.updateItemCount(1, 0)

        // then
        verify(exactly = 1) { cartRepository.updateCount(1, 0) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
    }

    @Test
    fun `아이템의 선택을 변경한다`() {
        // given

        // when
        presenter.updateItemCheck(1, true)

        // then
        verify(exactly = 1) { cartRepository.updateChecked(1, true) }
        verify(exactly = 1) { view.setBottom(any(), any()) }
        verify(exactly = 1) { view.setAllItemCheck(any()) }
    }
}
