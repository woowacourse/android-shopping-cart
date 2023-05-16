package woowacourse.shopping.home

import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts
import woowacourse.shopping.presentation.ui.home.presenter.HomeContract
import woowacourse.shopping.presentation.ui.home.presenter.HomePresenter

class HomePresenterTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = HomePresenter(view, productRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun DB로부터_가져온_상품목록을_뷰로_넘겨준다() {
        // given
        every { productRepository.getRecentlyViewedProducts(any()) } returns recentlyViewProducts
        every { productRepository.getProducts(any(), any()) } returns products

        // when
        presenter.fetchAllProductsOnHome()

        // then
        verify { productRepository.getRecentlyViewedProducts(any()) }
        verify { productRepository.getProducts(any(), any()) }
        verify { view.setUpProductsOnHome(listOf(wrappedRecentProducts) + wrappedProducts + showMoreButton) }
    }

    @Test
    fun 추가_데이터_요청_시_DB에서_데이터를_뷰로_넘겨준다() {
        // given
        every { productRepository.getProducts(any(), any()) } returns products

        // when
        presenter.fetchMoreProducts()

        // then
        verify { productRepository.getProducts(any(), any()) }
        verify { view.setUpMoreProducts(wrappedProducts) }
    }

    companion object {
        private val recentlyViewProducts = listOf<Product>(Product(0, "", "test", 999))
        private val products = listOf(Product(0, "", "test", 999))
        private val wrappedRecentProducts = RecentlyViewedProducts(recentlyViewProducts)
        private val wrappedProducts = products.map { Products(it) }
        private val showMoreButton = ShowMoreProducts
    }
}
