package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.presentation.cart.CartContract
import woowacourse.shopping.presentation.cart.CartPresenter
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.repository.CartRepository

class CartPresenterTest {
    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository)
    }

    private fun makeNotOrderedCartProductInfoList(): CartProductInfoList {
        return CartProductInfoList(
            List(5) { CartProductInfo(makeTestProduct(it), 1) },
        )
    }

    private fun makeOrderedCartProductInfoList(): CartProductInfoList {
        return CartProductInfoList(
            List(5) { CartProductInfo(makeTestProduct(it), 1, true) },
        )
    }

    private fun makeTestProduct(id: Int): Product {
        return Product(id, "", "", Price(1000))
    }

    @Test
    fun 페이지의_0번째_상품을_삭제할_수_있다() {
        // given
        val initCartProductInfoList = makeNotOrderedCartProductInfoList()
        val deleteItem = initCartProductInfoList.items[0]
        presenter = CartPresenter(view, cartRepository, initCartProductInfoList)
        // when
        presenter.deleteProductItem(0)
        val actual = presenter.loadedCartProducts.value.items.contains(deleteItem)
        // then
        verify { cartRepository.deleteCartProductId(0) }
        assertEquals(actual, false)
    }

    @Test
    fun 페이지의_0번째_상품의_count를_업데이트_할_수_있다() {
        // given
        val initCartProductInfoList = makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, initCartProductInfoList)
        // when
        presenter.updateProductCount(0, 3)
        val actual = presenter.loadedCartProducts.value.items[0].count
        // then
        verify { cartRepository.updateCartProductCount(0, 3) }
        assertEquals(actual, 3)
    }

    @Test
    fun 전체_체크박스를_체크하면_페이지에_있는_모든_상품을_주문목록에_추가한다() {
        // given
        val initCartProductInfoList = makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, initCartProductInfoList)
        // when
        presenter.changeCurrentPageProductsOrder()
        val actualFirst = presenter.loadedCartProducts.value.items.first().isOrdered
        val actualLast = presenter.loadedCartProducts.value.items.last().isOrdered
        // then
        assertEquals(true, actualFirst)
        assertEquals(true, actualLast)
    }

    @Test
    fun 전체_체크박스를_해제하면_페이지에_있는_모든_상품을_주문목록에_추가한다() {
        // given
        val initCartProductInfoList = makeOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, initCartProductInfoList)
        // when
        presenter.changeCurrentPageProductsOrder()
        val actualFirst = presenter.loadedCartProducts.value.items.first().isOrdered
        val actualLast = presenter.loadedCartProducts.value.items.last().isOrdered
        // then
        assertEquals(false, actualFirst)
        assertEquals(false, actualLast)
    }

    @Test
    fun 현재_페이지의_카트_목록을_총_로드된_카트목록에_추가한다() {
        // given
        presenter = CartPresenter(
            view = view,
            cartRepository = cartRepository,
            initCartProductList = CartProductInfoList(listOf()),
            initPage = 1,
        )
        every {
            cartRepository.getCartProductsInfo(
                5,
                0,
            )
        } returns makeNotOrderedCartProductInfoList()
        // when
        presenter.loadCurrentPageProducts()
        // then
        assertEquals(
            presenter.loadedCartProducts.value.items.first(),
            makeNotOrderedCartProductInfoList().items.first(),
        )
        assertEquals(
            presenter.loadedCartProducts.value.items.last(),
            makeNotOrderedCartProductInfoList().items.last(),
        )
    }

    @Test
    fun 현재_페이지의_화면을_업데이트한다() {
        // given
        presenter = CartPresenter(
            view = view,
            cartRepository = cartRepository,
            initCartProductList = makeNotOrderedCartProductInfoList(),
            initPage = 1,
        )
        // when
        presenter.updateCurrentPageCartView()
        // then
        val actual = makeNotOrderedCartProductInfoList().items.map { it.toPresentation() }
        verify { view.setCartItems(actual) }
    }

    @Test
    fun `0번째_상품의_체크박스를_체크하면_주문목록에_추가된다`() {
        // given
        presenter = CartPresenter(
            view = view,
            cartRepository = cartRepository,
            initCartProductList = makeNotOrderedCartProductInfoList(),
            initPage = 1,
        )
        // when
        presenter.addProductInOrder(0)
        // then
        val actual = presenter.loadedCartProducts.value.items[0].isOrdered
        assertEquals(true, actual)
    }

    @Test
    fun `0번째_상품의_체크박스를_해제하면_주문목록에서_제거된다`() {
        // given
        presenter = CartPresenter(
            view = view,
            cartRepository = cartRepository,
            initCartProductList = makeNotOrderedCartProductInfoList(),
            initPage = 1,
        )
        // when
        presenter.deleteProductInOrder(0)
        // then
        val actual = presenter.loadedCartProducts.value.items[0].isOrdered
        assertEquals(false, actual)
    }

    @Test
    fun 다음_페이지에_상품이_있으면_오른쪽페이지_버튼상태를_true로_한다() {
        // given
        every {
            cartRepository.getCartProductsInfo(
                5,
                5,
            )
        } returns makeNotOrderedCartProductInfoList()
        // when
        presenter.checkPlusPageAble()
        // then
        verify { view.setUpPlusPageState(true) }
    }

    @Test
    fun 다음_페이지에_상품이_없으면_오른쪽페이지_버튼상태를_false로_한다() {
        // given
        every { cartRepository.getCartProductsInfo(5, 5) } returns CartProductInfoList(listOf())
        // when
        presenter.checkPlusPageAble()
        // then
        verify { view.setUpPlusPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이라면_왼쪽버튼상태를_false로_한다() {
        // when
        presenter.checkMinusPageAble()
        // then
        verify { view.setUpMinusPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이_아니라면_왼쪽버튼상태를_true로_한다() {
        // when
        presenter.plusPage()
        presenter.checkMinusPageAble()
        // then
        verify { view.setUpMinusPageState(true) }
    }

    @Test
    fun 오른쪽_버튼을_페이지를_1_증가시킨다() {
        // given
        presenter = CartPresenter(view, cartRepository, initPage = 1)
        // when
        presenter.plusPage()
        val actual = presenter.paging.currentPage.value.value
        // then
        assertEquals(2, actual)
    }

    @Test
    fun 왼쪽_버튼을_누르면_페이지를_1_감소시킨다() {
        // given
        presenter = CartPresenter(view, cartRepository, initPage = 2)
        // when
        presenter.minusPage()
        val actual = presenter.paging.currentPage.value.value
        // then
        assertEquals(1, actual)
    }
}
