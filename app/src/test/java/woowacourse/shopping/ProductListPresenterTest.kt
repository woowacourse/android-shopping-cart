package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel
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

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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
        every { recentProductRepository.getRecentProducts(10) } returns productModels.map { it.toDomain() }
        // when
        presenter.updateRecentProductItems()
        // then
        verify { view.loadRecentProductModels(productModels) }
    }

    @Test
    fun 카트정보를_불러와_카트_갯수를_표시한다() {
        // given
        every { cartRepository.getAllCartProductsInfo() } returns makeTestCartProductInfoList()
        // when
        presenter.updateCartProductInfoList()
        // then
        assertEquals(10, presenter.cartProductInfoList.value.count)
    }

    @Test
    fun 카트_상품의_개수가_0이라면_상품을_삭제한다() {
        // given
        val productModel = ProductModel(1, "", "", 1000)
        // when
        presenter.updateCartProductCount(productModel, 0)
        // then
        verify { cartRepository.deleteCartProductId(1) }
    }

    @Test
    fun 카트_상품의_개수가_0이_아니라면_상품개수를_업데이트한다() {
        // given
        val productModel = ProductModel(1, "", "", 1000)
        // when
        presenter.updateCartProductCount(productModel, 1)
        // then
        verify { cartRepository.updateCartProductCount(1, 1) }
    }

    private fun makeTestCartProductInfoList(): CartProductInfoList {
        val product = Product(1, "", "", Price(100))
        return CartProductInfoList(List(5) { CartProductInfo(product, 2) })
    }
}
