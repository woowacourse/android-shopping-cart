package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.ShoppingCartProduct
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
    private lateinit var products: List<ShoppingCartProduct>

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(
            view = view,
            repository = repository,
        )
        products = listOf(
            ShoppingCartProduct(name = "아메리카노"),
            ShoppingCartProduct(name = "밀크티"),
        )
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 받아와서 뷰를 초기화한다`() {
        // given
        every { repository.selectShoppingCartProducts(any(), any()) } returns products

        // when
        presenter.loadShoppingCartProducts()

        // then
        val expected = products.map { it.toUiModel() }
        verify { view.setUpShoppingCartView(expected, any(), any()) }
    }

    @Test
    fun `상품을 삭제하면 저장소에게 삭제할 상품의 아이디를 전해준다`() {
        // when
        presenter.removeShoppingCartProduct(id = 1)

        // then
        verify { repository.deleteFromShoppingCart(id = 1) }
    }

    @Test
    fun `상품을 추가하면 저장소에서 상품 정보를 받아서 뷰에서 더 많은 상품을 보여준다`() {
        // given
        every { repository.selectShoppingCartProducts(any(), any()) } returns products

        // when
        presenter.addShoppingCartProducts()

        // then
        val expected = products.map { it.toUiModel() }
        verify { view.showMoreShoppingCartProducts(products = expected) }
    }
}
