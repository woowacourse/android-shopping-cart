package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productlist.ProductListContract
import woowacourse.shopping.presentation.productlist.ProductListPresenter

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View
    private val productRepository: ProductRepository = mockk()
    private val recentProductRepository: RecentProductIdRepository = mockk()

    @Before
    fun setUp() {
        view = mockk()
    }

    @Test
    fun Presenter를_생성할때_전체_상품과_최근_상품을_초기화_한다() {
        // given
        val slotProductList = slot<List<ProductModel>>()
        val slotRecentProductList = slot<List<ProductModel>>()

        every { productRepository.products } returns listOf()
        every { productRepository.getProductsWithRange(0, 20) } returns listOf()
        every { recentProductRepository.getRecentProductIds(10) } returns listOf()
        every { view.initProductModels(capture(slotProductList)) } just runs
        every { view.initRecentProductModels(capture(slotRecentProductList)) } just runs
        // when
        presenter = ProductListPresenter(view, productRepository, recentProductRepository)
        // then
        assertEquals(slotProductList.captured.size, 0)
        assertEquals(slotRecentProductList.captured.size, 0)
        verify { view.initProductModels(listOf()) }
        verify { view.initRecentProductModels(listOf()) }
    }

    @Test
    fun 상품_목록을_업데이트한다() {
        // given
        presenter = generatePresenter()
        val slot = slot<List<ProductModel>>()
        every { productRepository.getProductsWithRange(20, 20) } returns listOf()
        every { view.setProductModels(capture(slot)) } just runs
        // when
        presenter.updateProducts()
        // then
        assertEquals(20, slot.captured.size)
        verify { view.setProductModels(slot.captured) }
    }

    @Test
    fun 최근_상품_목록을_업데이트한다() {
        // given
        presenter = generatePresenter()
        val slot = slot<List<ProductModel>>()
        every { recentProductRepository.getRecentProductIds(10) } returns List(10) { return@List 10 }
        every { productRepository.findProductById(10) } returns Product(1, "", "", Price(100))
        every { view.setRecentProductModels(capture(slot)) } just runs
        // when
        presenter.updateRecentProducts()
        // then
        assertEquals(10, slot.captured.size)
        verify { view.setRecentProductModels(slot.captured) }
    }

    @Test
    fun 최근_상품_목록_아이디를_저장한다() {
        // given
        val id = 10
        presenter = generatePresenter()
        every { recentProductRepository.deleteRecentProductId(id) } just runs
        every { recentProductRepository.addRecentProductId(id) } just runs
        // when
        presenter.saveRecentProductId(id)
        // then
        verify { recentProductRepository.addRecentProductId(id) }
    }

    private fun generatePresenter(): ProductListPresenter {
        every { productRepository.products } returns listOf()
        every { productRepository.getProductsWithRange(0, 20) } returns
            List(20) { Product(1, "", "", Price(100)) }
        every { recentProductRepository.getRecentProductIds(10) } returns listOf()
        every { view.initProductModels(any()) } just runs
        every { view.initRecentProductModels(any()) } just runs
        // when
        return ProductListPresenter(view, productRepository, recentProductRepository)
    }
}
