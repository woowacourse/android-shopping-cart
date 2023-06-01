package woowacourse.shopping

import com.shopping.domain.Cart
import com.shopping.domain.CartProduct
import com.shopping.domain.CartRepository
import com.shopping.domain.PageNumber
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var presenter: CartContract.Presenter

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun `장바구니 레포지토리로 부터 데이터를 가져와 뷰에 전달한다`() {
        // given
        val data = List(5) { CartProduct(product = ProductUIModel.dummy.toDomain()) }
        val unitSize = 5
        val pageNumber = 1
        every { cartRepository.getUnitData(unitSize, pageNumber) } returns data
        val cart = Cart(data)
        justRun { view.setTotalPrice(cart.getPickedProductsTotalPrice()) }
        justRun { view.setCartProducts(data.map { it.toUIModel() }) }
        justRun { view.setAllChecked(cart.isAllPicked()) }
        justRun { view.setOrderProductTypeCount(cart.getTotalPickedProductsCount()) }

        // when
        presenter.fetchCartProducts()

        // then
        verify { cartRepository.getUnitData(unitSize, pageNumber) }
        verify { view.setTotalPrice(cart.getPickedProductsTotalPrice()) }
        verify { view.setCartProducts(data.map { it.toUIModel() }) }
        verify { view.setAllChecked(Cart(data).isAllPicked()) }
        verify { view.setOrderProductTypeCount(cart.getTotalPickedProductsCount()) }
    }

    @Test
    fun `장바구니의 상품을 삭제한다`() {
        // given
        val cartProductUIModel = CartProductUIModel.dummy
        val position = 1
        justRun { cartRepository.remove(cartProductUIModel.product.id) }
        justRun { view.removeAdapterData(cartProductUIModel, any()) }
        justRun { view.setAllChecked(any()) }
        justRun { view.setOrderProductTypeCount(any()) }

        // when
        presenter.removeProduct(cartProductUIModel)

        // then
        verify { cartRepository.remove(cartProductUIModel.product.id) }
        verify { view.removeAdapterData(cartProductUIModel, any()) }
        verify { view.setAllChecked(any()) }
    }

    @Test
    fun `1페이지를 표시하고 있고 다음 페이지가 보여줄 장바구니 아이템이 없을때 다음페이지로 이동하려하면 2 페이지로 넘어가지 않는다`() {
        // given
        val pageNumber: PageNumber = mockk()
        every { cartRepository.getSize() } returns 1
        every { pageNumber.value } returns 2

        // when
        presenter.goNextPage()

        // then
        verify(inverse = true) { presenter.updatePageNumber() }
        verify(inverse = true) { pageNumber.nextPage() }
    }

    @Test
    fun `1페이지를 표시하고 있고 다음 페이지가 보여줄 장바구니 아이템이 있을때 다음페이지로 이동하려하면 2 페이지로 넘어간다`() {
        // given
        val pageNumber: PageNumber = mockk()
        val currentPageNumber = 1
        val nextPageNumber = 2
        every { cartRepository.getSize() } returns 6
        every { pageNumber.value } returns currentPageNumber
        every { pageNumber.nextPage() } returns PageNumber(nextPageNumber)
        justRun { view.showPageNumber(nextPageNumber) }
        every { cartRepository.getUnitData(any(), any()) } returns emptyList()
        justRun { view.setCartProducts(any()) }
        justRun { view.setAllChecked(any()) }

        // when
        presenter.goNextPage()

        // then
        verify { presenter.updatePageNumber() }
        verify { view.showPageNumber(nextPageNumber) }
    }

    @Test
    fun `장바구니의 이전 페이지를 불러온다`() {
        // given
        val pageNumber = mockk<PageNumber>(relaxed = true)

        every { pageNumber.previousPage() } returns PageNumber(1)
        justRun { presenter.updatePageNumber() }
        justRun { view.setAllChecked(any()) }
        every { cartRepository.getSize() } returns 6
        every { cartRepository.getUnitData(any(), any()) } returns emptyList()
        justRun { view.setCartProducts(any()) }

        // when
        presenter.goPreviousPage()

        // then
        verify { presenter.updatePageNumber() }
        verify { view.setAllChecked(any()) }
    }

    @Test
    fun `페이지 숫자를 업데이트 한다`() {
        // given
        val pageNumber = mockk<PageNumber>()
        every { pageNumber.value } returns 1
        justRun { view.showPageNumber(1) }

        // when
        presenter.updatePageNumber()

        // then
        verify { view.showPageNumber(1) }
    }

    @Test
    fun `상품 하나의 선택 상태를 변경할 수 있다`() {
        // given
        val cart = mockk<Cart>(relaxed = true)

        justRun { cartRepository.updateProductIsPicked(any(), true) }
        justRun { view.setAllChecked(any()) }
        justRun { view.setOrderProductTypeCount(cart.getTotalPickedProductsCount()) }
        justRun { view.setTotalPrice(cart.getPickedProductsTotalPrice()) }

        // when
        presenter.updateIsPickAllProduct(true)

        // then
        verify { view.setAllChecked(any()) }
        verify { view.setOrderProductTypeCount(any()) }
        verify { view.setTotalPrice(any()) }
    }

    @Test
    fun `변경하려는 장바구니 상품의 개수가 0이하라면 업데이트 하지 않는다`() {
        // given

        // when
        presenter.updateCartProductCount(CartProductUIModel.dummy, 0)

        // then
        verify(inverse = true) { cartRepository.updateProductCount(any(), any()) }
        verify(inverse = true) { view.setTotalPrice(any()) }
    }

    @Test
    fun `변경하려는 장바구니 상품의 개수가 0이하가 아니라면 업데이트 할 수 있다`() {
        // given
        val cart = mockk<Cart>()
        val currentPageProducts = mockk<Cart>()

        every { cart.updateProductCount(any(), 1) }
        every { currentPageProducts.updateProductCount(any(), 1) }
        justRun { cartRepository.updateProductCount(any(), 1) }
        justRun { view.setTotalPrice(any()) }

        // when
        presenter.updateCartProductCount(CartProductUIModel.dummy, 1)

        // then
        verify { cartRepository.updateProductCount(any(), 1) }
        verify { view.setTotalPrice(any()) }
    }
}
