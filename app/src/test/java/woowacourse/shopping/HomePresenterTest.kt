package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ui.home.HomeContract
import woowacourse.shopping.presentation.ui.home.HomePresenter

class HomePresenterTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var product: Product
    private lateinit var products: List<Product>

    @Before
    fun setUp() {
        view = mockk<HomeContract.View>(relaxed = true)
        product = Product(
            id = 1,
            name = "BMW i8",
            price = 13000,
            itemImage = "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png",
        )
        products = listOf(product)
        productRepository = mockk()
        every { productRepository.getRecentlyViewedProducts(10) } returns products
        every { productRepository.isLastProduct(1) } returns true
        presenter = HomePresenter(view, productRepository)
    }

    @Test
    fun `상품들을 가져와서 뷰에 세팅해준다`() {
        // given
        val slot = slot<Long>()
        every { productRepository.getProducts(10, capture(slot)) } returns products

        // when
        presenter.getProducts()
        val lastProductId = slot.captured

        // then
        assertEquals(0, lastProductId)
        verify(exactly = 1) { productRepository.getProducts(10, lastProductId) }
        verify(exactly = 1) { productRepository.isLastProduct(1) }
        verify(exactly = 1) { view.setProducts(products, true) }
    }

    @Test
    fun `최근 조회한 상품 목록을 가져와서 뷰에 세팅해준다`() {
        // given
        val slot = slot<List<Product>>()
        every { view.setRecentlyViewed(capture(slot)) } returns Unit

        // when
        presenter.getRecentlyViewed()
        val actual = slot.captured

        // then
        verify(exactly = 1) { productRepository.getRecentlyViewedProducts(10) }
        verify(exactly = 1) { view.setRecentlyViewed(products) }
        assertEquals(products, actual)
    }
}
