package woowacourse.shopping.ui.productdetail

import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.FakeCartRepository
import woowacourse.shopping.database.FakeProductRepository

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter =
            ProductDetailPresenter(view, FakeProductRepository, FakeCartRepository)
    }

    @Test
    fun 장바구니에_상품을_담으면_장바구니_목록에_해당_상품이_추가된다() {
        presenter.addProductToCart(2, 1)

        val actual = FakeCartRepository.findAll().map { it.id }.contains(2)
        assertTrue(actual)
    }
}
