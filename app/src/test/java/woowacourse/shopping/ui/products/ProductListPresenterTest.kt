package woowacourse.shopping.ui.products

import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.FakeProductRepository
import woowacourse.shopping.database.FakeRecentlyViewedProductRepository

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter =
            ProductListPresenter(view, FakeRecentlyViewedProductRepository, FakeProductRepository)
    }

    @Test
    fun 상품을_선택하면_최근_조회한_상품_목록에_해당_상품이_추가된다() {
        presenter.addRecentlyViewedProduct(1)

        val actual = FakeRecentlyViewedProductRepository.findAll().map { it.id }.contains(1)
        assertTrue(actual)
    }
}
