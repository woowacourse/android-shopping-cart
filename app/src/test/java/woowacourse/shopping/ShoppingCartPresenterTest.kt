package woowacourse.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.productdetail.ProductUiModel
import woowacourse.shopping.shoppingcart.ShoppingCartContract
import woowacourse.shopping.shoppingcart.ShoppingCartPresenter

class ShoppingCartPresenterTest {

    private lateinit var presenter: ShoppingCartContract.Presenter
    private lateinit var view: ShoppingCartContract.View
    private lateinit var repository: ShoppingRepository

    @Before
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = ShoppingCartPresenter(
            view = view,
            repository = repository,
        )
    }

    @Test
    fun `저장소에서 장바구니에 담긴 상품들을 받아와서 뷰를 초기화한다`() {
        // given
        val slot = slot<List<ProductUiModel>>()
        every { view.setUpShoppingCartView(capture(slot), any()) } just Runs
        every { repository.loadShoppingCartProducts() } returns listOf(
            ProductUiModel(name = "아메리카노"),
            ProductUiModel(name = "밀크티"),
        )

        // when
        presenter.loadShoppingCartProducts()

        // then
        val actual = slot.captured
        val expected = listOf(
            ProductUiModel(name = "아메리카노"),
            ProductUiModel(name = "밀크티"),
        )

        assertEquals(expected, actual)
        verify { view.setUpShoppingCartView(actual, any()) }
    }
}
