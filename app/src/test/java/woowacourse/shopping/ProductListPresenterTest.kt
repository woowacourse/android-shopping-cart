package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.productlist.ProductListContract
import woowacourse.shopping.presentation.productlist.ProductListPresenter
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View
    private val productRepository: ProductRepository = mockk(relaxed = true)
    private val recentProductRepository: RecentProductRepository = mockk(relaxed = true)
    private val cartRepository: CartRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter =
            ProductListPresenter(view, productRepository, recentProductRepository, cartRepository)
    }

    @Test
    fun 상품_목록을_업데이트한다() {
        // given
        every { productRepository.getProductsWithRange(20, 20) } returns listOf()
        // when
        presenter.updateProductItems()
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
        presenter.updateRecentProductItems()
        // then
        verify { view.loadRecentProductModels(productModels) }
    }

    @Test
    fun 최근_상품_목록_아이디를_저장한다() {
        // given
        val id = 10
        // when
        presenter.saveRecentProduct(id)
        // then
        verify { recentProductRepository.addRecentProductId(id) }
    }

    @Test
    fun 카트_갯수를_표시한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeTestCartProductInfoList()
        // when
        presenter.updateCartCount()
        // then
        verify { view.showCartCount(10) }
    }

    private fun makeTestCartProductInfoList(): CartProductInfoList {
        val product = Product(1, "", "", Price(100))
        return CartProductInfoList(List(5) { CartProductInfo(product, 2) })
    }
}
