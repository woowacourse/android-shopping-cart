package woowacourse.shopping.ui.productdetail

import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.FakeCartRepository
import woowacourse.shopping.database.FakeProductRepository
import woowacourse.shopping.database.FakeRecentlyViewedProductRepository

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter =
            ProductDetailPresenter(
                view,
                FakeProductRepository,
                FakeCartRepository,
                FakeRecentlyViewedProductRepository,
            )
    }

    @Test
    fun 장바구니에_상품을_담으면_장바구니_목록에_해당_상품이_추가된다() {
        presenter.addProductToCart(2, 1)

        val actual = FakeCartRepository.findAll().map { it.id }.contains(2)
        assertTrue(actual)
    }

    @Test
    fun 상품을_선택하면_최근_조회한_상품_목록에_해당_상품이_추가된다() {
        presenter.addRecentlyViewedProduct(1)

        val actual = FakeRecentlyViewedProductRepository.findAll().map { it.id }.contains(1)
        assertTrue(actual)
    }
}
