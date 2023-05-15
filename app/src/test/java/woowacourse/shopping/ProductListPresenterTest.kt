package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.productlist.ProductListContract
import woowacourse.shopping.presentation.productlist.ProductListPresenter

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View
    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val recentProductRepository: RecentProductIdRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = ProductListPresenter(view, productRepository, recentProductRepository)
    }

    @Test
    fun 상품_목록을_업데이트한다() {
        // given
        every { productRepository.getProductsWithRange(20, 20) } returns listOf()
        // when
        presenter.updateProducts()
        // then
        verify { view.loadProductModels(listOf()) }
    }

    @Test
    fun 최근_상품_목록을_업데이트한다() {
        // given
        val product = Product(1, "", "", Price(100))
        val productModels = List(10) { product.toPresentation() }
        every { recentProductRepository.getRecentProductIds(10) } returns List(10) { return@List 10 }
        every { productRepository.findProductById(10) } returns Product(1, "", "", Price(100))
        // when
        presenter.updateRecentProducts()
        // then
        verify { view.loadRecentProductModels(productModels) }
    }

    @Test
    fun 최근_상품_목록_아이디를_저장한다() {
        // given
        val id = 10
        // when
        presenter.saveRecentProductId(id)
        // then
        verify { recentProductRepository.addRecentProductId(id) }
    }
}
