package woowacourse.shopping.feature.main

import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import io.mockk.*
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

internal class MainPresenterTest {
    private lateinit var view: MainContract.View
    private lateinit var presenter: MainContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun init() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentProductRepository = mockk()
        presenter = MainPresenter(view, productRepository, cartRepository, recentProductRepository)
        ProductLocalCache.clear()
    }

    @Test
    fun `처음에 상품 목록을 제대로 불러와서 상품을 화면에 띄운다`() {
        // given
        val successSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.getFirstProducts(onSuccess = capture(successSlot), any())
        } answers {
            successSlot.captured.invoke(mockProducts.take(20)) // 기억한 람다를 실행시킨다. 이때 데이터 20개를 넘겨줌
        }
        val slot = slot<List<ProductUiModel>>()
        every { view.setProducts(capture(slot)) } just Runs

        // when
        presenter.loadProducts()

        // then
        val actual = slot.captured.map { it.toDomain() }
        val expected = mockProducts.take(20)
        assert(actual == expected)
        verify { view.setProducts(any()) }
    }

    @Test
    fun `장바구니 화면으로 이동한다`() {
        // given
        every { view.showCartScreen() } just Runs

        // when
        presenter.moveToCart()

        // then
        verify { view.showCartScreen() }
    }

    @Test
    fun `상품 목록을 이어서 더 불러와서 화면에 추가로 띄운다`() {
        // given
        val successSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.getFirstProducts(onSuccess = capture(successSlot), any())
        } answers {
            successSlot.captured.invoke(mockProducts.take(20)) // 기억한 람다를 실행시킨다. 이때 데이터 20개를 넘겨줌
        }
        every { view.setProducts(any()) } just Runs
        presenter.loadProducts()

        val lastProductId = 20L
        val nextSuccessSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.getNextProducts(
                lastProductId,
                capture(nextSuccessSlot),
                any()
            )
        } answers {
            nextSuccessSlot.captured.invoke(mockProducts.subList(20, 40))
        }

        val slot = slot<List<ProductUiModel>>()
        every { view.setProducts(capture(slot)) } just Runs

        // when
        presenter.loadMoreProduct()

        // then
        val actual = slot.captured.map { it.toDomain() }
        val expected = mockProducts.subList(0, 40)

        assert(actual == expected)
        verify { view.setProducts(any()) }
    }

    @Test
    fun `최근 본 상품 목록을 가져와서 화면에 띄운다`() {
        // given
        every { recentProductRepository.getAll() } returns mockRecentProducts
        val slot = slot<List<RecentProductUiModel>>()
        every { view.updateRecent(capture(slot)) } just Runs

        // when
        presenter.loadRecent()

        // then
        val actual = slot.captured.map { it.productUiModel.toDomain() }
        val expected = mockRecentProducts.map { it.product }
        assert(actual == expected)
        verify { view.updateRecent(any()) }
    }

    private val mockProducts = productsDatasource

    private val mockRecentProducts = List(20) {
        RecentProduct(
            mockProducts[it],
            LocalDateTime.now().plusMinutes(it.toLong())
        )
    }
}
