// package woowacourse.shopping.presentation.cart
//
// import io.mockk.every
// import io.mockk.justRun
// import io.mockk.mockk
// import io.mockk.slot
// import io.mockk.verify
// import junit.framework.TestCase.assertEquals
// import org.junit.Before
// import org.junit.Test
// import woowacourse.shopping.data.respository.cart.CartRepository
// import woowacourse.shopping.data.respository.product.ProductRepository
// import woowacourse.shopping.data.respository.product.ProductWebServer
// import woowacourse.shopping.presentation.CartFixture
// import woowacourse.shopping.presentation.ProductFixture
// import woowacourse.shopping.presentation.model.CartModel
// import woowacourse.shopping.presentation.view.cart.CartContract
// import woowacourse.shopping.presentation.view.cart.CartPresenter
//
// class CartPresenterTest {
//    private lateinit var presenter: CartContract.Presenter
//    private lateinit var view: CartContract.View
//    private lateinit var cartRepository: CartRepository
//    private lateinit var productRepository: ProductRepository
//
//    @Before
//    fun setUp() {
//        view = mockk(relaxed = true)
//        cartRepository = mockk()
//
//        productRepository = mockk()
//        every { productRepository.loadDataById(0L) } returns ProductFixture.getData()
//        every { productRepository.loadData() } returns listOf(ProductFixture.getData())
//
//        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
//    }
//
//    @Test
//    fun `장바구니 데이터를 받아와 보여준다`() {
//        // given
//        justRun { view.setEnableLeftButton(false) }
//        justRun { view.setEnableRightButton(false) }
//
//        presenter = CartPresenter(view, cartRepository)
//
//        val slot = slot<List<CartModel>>()
//        justRun { view.setCartItemsView(capture(slot)) }
//
//        // when
//        presenter.loadCartItems()
//
//        // then
//        val actual = slot.captured
//        verify { view.setEnableLeftButton(false) }
//        verify { view.setEnableRightButton(false) }
//        verify { view.setCartItemsView(actual) }
//    }
//
//    @Test
//    fun `카트 데이터가 하나 삭제된다`() {
//        // given
//        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
//        justRun { cartRepository.deleteCartByCartId(1) }
//        justRun { view.setEnableLeftButton(false) }
//        justRun { view.setEnableRightButton(false) }
//
//        presenter = CartPresenter(view, cartRepository)
//
//        val slot = slot<List<CartModel>>()
//        justRun { view.setChangedCartItemsView(capture(slot)) }
//
//        // when
//        presenter.deleteCartItem(1)
//
//        // then
//        val actual = slot.captured
//        val expected = CartFixture.getFixture().filter { it.id != 1L }
//        assertEquals(expected.size, actual.size)
//        verify { cartRepository.deleteCartByCartId(1) }
//        verify { view.setEnableLeftButton(false) }
//        verify { view.setEnableRightButton(false) }
//        verify { view.setChangedCartItemsView(actual) }
//    }
//
//    @Test
//    fun `상품의 개수를 추가할 수 있다`() {
//        // given
//        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
//        justRun { cartRepository.updateCartCountByCartId(1L, 2) }
//        presenter = CartPresenter(view, cartRepository)
//
//        // when
//        presenter.updateProductCount(1L, 2)
//
//        // then
//        verify { cartRepository.updateCartCountByCartId(1L, 2) }
//    }
//
//    @Test
//    fun `상품의 개수가 0이면 상품이 장바구니에서 제거된다`() {
//        // given
//        val carts = CartFixture.getFixture()
//        every { cartRepository.getAllCarts() } returns carts
//        justRun { cartRepository.updateCartCountByCartId(2L, 0) }
//        justRun { cartRepository.deleteCartByCartId(2L) }
//        justRun { view.setEnableLeftButton(false) }
//        justRun { view.setEnableRightButton(false) }
//
//        val slot = slot<List<CartModel>>()
//        justRun { view.setChangedCartItemsView(capture(slot)) }
//
//        presenter = CartPresenter(view, cartRepository)
//
//        // when
//        presenter.updateProductCount(2L, 0)
//
//        // then
//        val actual = slot.captured
//        val expected = 2
//        assertEquals(expected, actual.size)
//        verify { cartRepository.updateCartCountByCartId(2L, 0) }
//        verify { cartRepository.deleteCartByCartId(2L) }
//        verify { view.setEnableLeftButton(false) }
//        verify { view.setEnableRightButton(false) }
//        verify { view.setChangedCartItemsView(actual) }
//    }
//
//    @Test
//    fun `상품의 체크 상태를 갱신할 수 있다`() {
//        // given
//        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
//        justRun { cartRepository.updateCartCheckedByCartId(3L, true) }
//        justRun { view.setAllCartChecked(true) }
//
//        presenter = CartPresenter(view, cartRepository)
//
//        // when
//        presenter.updateProductChecked(3L, true)
//
//        // then
//        verify { cartRepository.updateCartCheckedByCartId(3L, true) }
//        verify { view.setAllCartChecked(true) }
//    }
//
//    @Test
//    fun `체크 되어있는 상품들의 총 가격을 보여준다`() {
//        // given
//        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
//
//        val slot = slot<Int>()
//        justRun { view.setTotalPriceView(capture(slot)) }
//
//        presenter = CartPresenter(view, cartRepository)
//
//        // when
//        presenter.calculateTotalPrice()
//
//        // then
//        val actual = slot.captured
//        val expected = 24_900 + 7_980
//        assertEquals(expected, actual)
//        verify { cartRepository.getAllCarts() }
//        verify { view.setTotalPriceView(actual) }
//    }
//
//    @Test
//    fun `2페이지에서 이전 페이지인 1페이지로 이동한다`() {
//        // given
//        val slot = slot<Int>()
//        justRun { view.setPageCountView(capture(slot)) }
//
//        presenter = CartPresenter(view, cartRepository, currentPage = 2)
//
//        // when
//        presenter.calculatePreviousPage()
//
//        // then
//        val actual = slot.captured
//        val expected = 1
//        assertEquals(expected, actual)
//        verify { view.setPageCountView(actual) }
//    }
//
//    @Test
//    fun `1페이지에서 다음 페이진 2페이지로 이동한다`() {
//        // given
//        val slot = slot<Int>()
//        justRun { view.setPageCountView(capture(slot)) }
//
//        presenter = CartPresenter(view, cartRepository, currentPage = 1)
//
//        // when
//        presenter.calculateNextPage()
//
//        // then
//        val actual = slot.captured
//        val expected = 2
//        assertEquals(expected, actual)
//        verify { view.setPageCountView(actual) }
//    }
//
//    companion object {
//        private const val DUMMY_JSON = """
//            "id" = 0,
//            "title" = "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
//            "price" = 24900,
//            "imageurl" = "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
//        """
//    }
// }
