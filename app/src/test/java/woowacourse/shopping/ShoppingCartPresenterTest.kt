package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.Product
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
    private lateinit var products: List<Product>

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        presenter = ShoppingCartPresenter(
            view = view,
            repository = repository,
        )
        products = listOf(
            Product(name = "아메리카노"),
            Product(name = "밀크티"),
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
    fun `저장소에서 지정된 아이디의 상품을 삭제한다`() {
        // when
        presenter.removeShoppingCartProduct(1)

        // then
        verify { repository.deleteFromShoppingCart(1) }
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 추가적으로 받아와서 뷰에 갱신한다`() {
        // given
        every { repository.selectShoppingCartProducts(any(), any()) } returns products

        // when
        presenter.readMoreShoppingCartProducts()

        // then
        val expected = products.map { it.toUiModel() }
        verify { view.showMoreShoppingCartProducts(expected) }
    }
}
