package woowacourse.shopping.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.cache.ProductLocalCache
import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.mapper.toPresentation
import java.time.LocalDateTime

internal class MainPresenterTest {
    private lateinit var view: MainContract.View
    private lateinit var presenter: MainContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentProductRepository = mockk()
        presenter = MainPresenter(productRepository, cartRepository, recentProductRepository)
        ProductLocalCache.clear()
    }

    @Test
    fun `처음에 상품 목록을 제대로 불러와서 상품을 화면에 띄운다`() {
        // given
        val successSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.fetchFirstProducts(onSuccess = capture(successSlot), any())
        } answers {
            successSlot.captured.invoke(mockProducts.take(20)) // 기억한 람다를 실행시킨다. 이때 데이터 20개를 넘겨줌
        }

        // when
        presenter.loadProducts()

        // then
        val actual = presenter.products.getOrAwaitValue()
        val expected = mockProducts.take(20).map { it.toPresentation() }
        assert(actual == expected)
    }

    @Test
    fun `장바구니 화면으로 이동한다`() {
        // when
        presenter.moveToCart()

        // then
        val actual = presenter.mainScreenEvent.getOrAwaitValue()
        assert(actual is MainContract.View.MainScreenEvent.ShowCartScreen)
    }

    @Test
    fun `상품 목록을 이어서 더 불러와서 화면에 추가로 띄운다`() {
        // given
        val successSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.fetchFirstProducts(onSuccess = capture(successSlot), any())
        } answers {
            successSlot.captured.invoke(mockProducts.take(20)) // 기억한 람다를 실행시킨다. 이때 데이터 20개를 넘겨줌
        }
        presenter.loadProducts() // 초깃값 준비

        val lastProductId = 20L
        val nextSuccessSlot = slot<(List<Product>) -> Unit>()
        every {
            productRepository.fetchNextProducts(
                lastProductId,
                capture(nextSuccessSlot),
                any()
            )
        } answers {
            nextSuccessSlot.captured.invoke(mockProducts.subList(20, 40))
        }

        // when
        presenter.loadMoreProduct()

        // then
        val actual = presenter.products.getOrAwaitValue()
        val expected = mockProducts.subList(0, 40).map { it.toPresentation() }

        assert(actual == expected)
    }

    @Test
    fun `최근 본 상품 목록을 가져와서 화면에 띄운다`() {
        // given
        every { recentProductRepository.getAll() } returns mockRecentProducts

        // when
        presenter.loadRecent()

        // then
        val actual = presenter.recentProducts.getOrAwaitValue()
        val expected = mockRecentProducts.map { it.toPresentation() }
        assert(actual == expected)
    }

    private val mockProducts = productsDatasource

    private val mockRecentProducts = List(20) {
        RecentProduct(
            mockProducts[it],
            LocalDateTime.now().plusMinutes(it.toLong())
        )
    }
}
