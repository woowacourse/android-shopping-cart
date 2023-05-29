package woowacourse.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import model.CartProduct
import model.Price
import model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.shoppingcart.ShoppingCartContract
import woowacourse.shopping.shoppingcart.ShoppingCartPresenter
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenterTest {

    private lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var view: ShoppingCartContract.View
    private lateinit var repository: ShoppingRepository
    private lateinit var cartProducts: List<CartProduct>

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(
            view = view,
            repository = repository,
        )
        cartProducts = listOf(
            CartProduct(Product(price = Price(1000)), 1, true),
            CartProduct(Product(price = Price(500)), 2, true),
        )
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 받아와서 뷰를 초기화한다`() {
        // given
        every { repository.selectShoppingCartProducts(any(), any()) } returns cartProducts

        // when
        presenter.loadShoppingCartProducts()

        // then
        val expected = listOf(
            CartProductUiModel(ProductUiModel(price = 1000), 1, true),
            CartProductUiModel(ProductUiModel(price = 500), 2, true),
        )

        verify { view.setUpShoppingCartView(expected, any()) }
    }

    @Test
    fun `저장소에서 지정된 아이디의 상품을 삭제한다`() {
        // when
        presenter.removeShoppingCartProduct(1)

        // then
        verify { repository.deleteFromShoppingCart(1) }
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 추가적으로 받아와서 뷰에 갱신한다`() {
        // given
        every { repository.selectShoppingCartProducts(any(), any()) } returns cartProducts

        // when
        presenter.readMoreShoppingCartProducts()

        // then
        val expected = listOf(
            CartProductUiModel(ProductUiModel(price = 1000), 1, true),
            CartProductUiModel(ProductUiModel(price = 500), 2, true),
        )
        verify { view.showMoreShoppingCartProducts(expected) }
    }

    @Test
    fun `물품의 수량을 더하면 저장소에 갱신한다`() {
        // given
        val slot = slot<Int>()
        every { repository.selectShoppingCartProductById(0) } returns CartProduct(count = 1)
        every { repository.updateShoppingCartCount(0, capture(slot)) } just Runs

        // when
        presenter.changeShoppingCartProductCount(0, true)

        // then
        val actual = slot.captured
        assertEquals(2, actual)
    }

    @Test
    fun `물품의 수량을 빼면 저장소에 갱신한다`() {
        // given
        val slot = slot<Int>()
        every { repository.selectShoppingCartProductById(0) } returns CartProduct(count = 2)
        every { repository.updateShoppingCartCount(0, capture(slot)) } just Runs

        // when
        presenter.changeShoppingCartProductCount(0, false)

        // then
        val actual = slot.captured
        assertEquals(1, actual)
    }

    @Test
    fun `해당 상품을 선택하면 저장소에 갱신된다`() {
        // given
        val slot = slot<Boolean>()
        every { repository.updateShoppingCartSelection(0, capture(slot)) } just Runs

        // when
        val expected = true
        presenter.changeShoppingCartProductSelection(0, expected)

        // then
        assertEquals(expected, slot.captured)
    }

    @Test
    fun `전체 체크 박스를 눌렀을 때, 모든 상품의 선택정보가 저장소에 갱신된다`() {
        // given
        val slot = slot<Boolean>()
        every { repository.updateShoppingCartSelection(any(), capture(slot)) } just Runs

        // when
        val expected = true
        presenter.checkAllBox(cartProducts.map { it.toUiModel() }, expected)

        // then
        assertEquals(expected, slot.captured)
    }

    @Test
    fun `전체 체크 박스를 눌렀을 때, 상품들에 맞게 하단의 주문 정보들을 업데이트한다`() {
        // given
        val totalPrice = slot<Int>()
        val selectedProductsSize = slot<Int>()
        every { repository.getSelectedShoppingCartProducts() } returns cartProducts
        every { view.updateTotalInfo(capture(totalPrice), capture(selectedProductsSize)) } just Runs

        // when
        presenter.checkAllBox(cartProducts.map { it.toUiModel() }, true)

        // then
        assertEquals(2000, totalPrice.captured)
        assertEquals(3, selectedProductsSize.captured)
    }

    @Test
    fun `모든 상품을 선택된 상태로 갱신한다`() {
        // given
        val slot = slot<Boolean>()
        every { repository.selectAllShoppingCartProducts() } returns cartProducts
        every { repository.updateShoppingCartSelection(any(), capture(slot)) } just Runs

        // when
        presenter.checkAllShoppingCartProducts()

        // then
        assertEquals(true, slot.captured)
    }
}
