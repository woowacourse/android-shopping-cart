package woowacourse.shopping.feature.main

import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import io.mockk.*
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.feature.main.product.MainProductItemModel
import woowacourse.shopping.feature.main.recent.RecentProductItemModel
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

internal class MainPresenterTest {
    private lateinit var view: MainContract.View
    private lateinit var presenter: MainContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun init() {
        view = mockk()
        productRepository = mockk()
        recentProductRepository = mockk()
        presenter = MainPresenter(view, productRepository, recentProductRepository)
        ProductLocalCache.clear()
    }

    @Test
    fun `처음에 상품 목록을 제대로 불러와서 상품을 화면에 띄운다`() {
        every { productRepository.getFirstProducts() } returns mockProducts.take(20)
        val slot = slot<List<ProductUiModel>>()
        every { view.addProducts(capture(slot)) } just Runs

        presenter.loadProducts()

        val actual = slot.captured.map { it.toDomain() }
        val expected = mockProducts.take(20)
        assert(actual == expected)
        verify { view.addProducts(any()) }
    }

    @Test
    fun `장바구니 화면으로 이동한다`() {
        every { view.showCartScreen() } just Runs

        presenter.moveToCart()

        verify { view.showCartScreen() }
    }

    @Test
    fun `상품 목록을 이어서 더 불러와서 화면에 추가로 띄운다`() {
        every { productRepository.getFirstProducts() } returns mockProducts.take(20)
        every { view.addProducts(any()) } just Runs
        presenter.loadProducts()

        val lastProductId = 20L
        every { productRepository.getNextProducts(lastProductId) } returns mockProducts.subList(
            20, 40
        )
        val slot = slot<List<ProductUiModel>>()
        every { view.addProducts(capture(slot)) } just Runs

        presenter.loadMoreProduct()

        val actual = slot.captured.map { it.toDomain() }
        val expected = mockProducts.subList(20, 40)

        assert(actual == expected)
        verify { view.addProducts(any()) }
    }

    @Test
    fun `최근 본 상품 목록을 가져와서 화면에 띄운다`() {
        every { recentProductRepository.getAll() } returns mockRecentProducts
        val slot = slot<List<RecentProductUiModel>>()
        every { view.updateRecent(capture(slot)) } just Runs

        presenter.loadRecent()

        val actual = slot.captured.map { it.productUiModel.toDomain() }
        val expected = mockRecentProducts.map { it.product }
        assert(actual == expected)
        verify { view.updateRecent(any()) }
    }

    private val mockProducts = productsDatasource

    private val mockRecentProducts = List(16) {
        RecentProduct(
            mockProducts[it],
            LocalDateTime.now().plusMinutes(it.toLong())
        )
    }
}
