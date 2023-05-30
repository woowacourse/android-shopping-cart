package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.cart.CartContract
import woowacourse.shopping.presentation.cart.CartPresenter
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.repository.CartRepository

class CartPresenterTest {
    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
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

    private fun makeTestCartProduct(
        id: Int,
        count: Int,
        isOrdered: Boolean = false,
    ): CartProductInfoModel {
        return CartProductInfoModel(makeTestProduct(id).toPresentation(), count, isOrdered)
    }

    @Test
    fun x버튼을_누르면_해당하는_상품을_삭제할_수_있다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.deleteProductItem(
            CartProductInfoModel(makeTestProduct(1).toPresentation(), 1),
        )
        // then
        verify { cartRepository.deleteCartProductId(1) }
        val expectedList = listOf(
            makeTestCartProduct(0, 1),
            makeTestCartProduct(2, 1),
            makeTestCartProduct(3, 1),
            makeTestCartProduct(4, 1),
        )
        verify { view.setCartItems(expectedList) }
    }

    @Test
    fun 수량_더하기_버튼을_누르면_해당하는_상품의_개수를_업데이트_할_수_있다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.updateProductCount(makeTestCartProduct(1, 2), 3)
        // then
        verify { cartRepository.updateCartProductCount(1, 3) }
    }

    @Test
    fun 전체_체크박스를_체크하면_페이지에_있는_모든_상품들을_주문목록에_추가한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.changeCurrentPageProductsOrder(true)
        // then
        val expectedList = listOf(
            makeTestCartProduct(0, 1, true),
            makeTestCartProduct(1, 1, true),
            makeTestCartProduct(2, 1, true),
            makeTestCartProduct(3, 1, true),
            makeTestCartProduct(4, 1, true),
        )
        verify { view.setCartItems(expectedList) }
    }

    @Test
    fun 전체_체크박스를_해제하면_페이지에_있는_모든_상품들의_주문목록을_해제한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.changeCurrentPageProductsOrder(false)
        // then
        val expectedList = listOf(
            makeTestCartProduct(0, 1, false),
            makeTestCartProduct(1, 1, false),
            makeTestCartProduct(2, 1, false),
            makeTestCartProduct(3, 1, false),
            makeTestCartProduct(4, 1, false),
        )
        verify { view.setCartItems(expectedList) }
    }

    @Test
    fun 현재_페이지의_상품들을_새로고침한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.refreshCurrentPage()
        // then
        val expectedItems = makeNotOrderedCartProductInfoList().items.map { it.toPresentation() }
        verify { view.setCartItems(expectedItems) }
    }

    @Test
    fun `해당하는_상품의_체크박스를_체크하면_주문목록에_추가된다`() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.addProductInOrder(makeTestCartProduct(1, 1))
        // then
        val expectedItems = listOf(
            makeTestCartProduct(0, 1),
            makeTestCartProduct(1, 1, true),
            makeTestCartProduct(2, 1),
            makeTestCartProduct(3, 1),
            makeTestCartProduct(4, 1),
        )
        verify { view.setCartItems(expectedItems) }
    }

    @Test
    fun `0번째_상품의_체크박스를_해제하면_주문목록에서_제거된다`() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.deleteProductInOrder(makeTestCartProduct(1, 1))
        // then
        val expectedItems = listOf(
            makeTestCartProduct(0, 1, true),
            makeTestCartProduct(1, 1, false),
            makeTestCartProduct(2, 1, true),
            makeTestCartProduct(3, 1, true),
            makeTestCartProduct(4, 1, true),
        )
        verify { view.setCartItems(expectedItems) }
    }

    @Test
    fun 다음_페이지에_상품이_있으면_오른쪽페이지_버튼상태를_true로_한다() {
        // given
        presenter = CartPresenter(view, cartRepository, 1)
        every { cartRepository.getCartProductsInfo(5, 5) } returns makeNotOrderedCartProductInfoList()
        // when
        presenter.checkPlusPageAble()
        // then
        verify { view.setUpPlusPageState(true) }
    }

    @Test
    fun 다음_페이지에_상품이_없으면_오른쪽페이지_버튼상태를_false로_한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, 1)
        // when
        presenter.checkPlusPageAble()
        // then
        verify { view.setUpPlusPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이라면_왼쪽버튼상태를_false로_한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.checkMinusPageAble()
        // then
        verify { view.setUpMinusPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이_아니라면_왼쪽버튼상태를_true로_한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, 2)
        // when
        presenter.checkMinusPageAble()
        // then
        verify { view.setUpMinusPageState(true) }
    }

    @Test
    fun 오른쪽_버튼을_페이지를_1_증가시킨다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeNotOrderedCartProductInfoList()
        presenter = CartPresenter(view, cartRepository, 1)
        // when
        presenter.plusPage()
        // then
        verify { view.setPage("2") }
    }

    @Test
    fun 왼쪽_버튼을_누르면_페이지를_1_감소시킨다() {
        // given
        presenter = CartPresenter(view, cartRepository, initPage = 2)
        // when
        presenter.minusPage()
        // then
        verify { view.setPage("1") }
    }
}
