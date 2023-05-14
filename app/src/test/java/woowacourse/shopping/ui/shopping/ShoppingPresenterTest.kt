package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

class ShoppingPresenterTest {

    private lateinit var view: ShoppingContract.View
    private lateinit var presenter: ShoppingContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentRepository: RecentRepository

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
    )

    private val fakeRecentProduct: RecentProduct = RecentProduct(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, productRepository, recentRepository)
    }

    @Test
    fun `상품을 불러와서 세팅한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeRecentProduct }
        val slot = slot<List<ProductsItemType>>()
        every { view.setProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()

        // then
        val capturedProducts = slot.captured
        assertTrue(capturedProducts.size == 12)
        verify(exactly = 1) { view.setProducts(capturedProducts) }
    }

    @Test
    fun `최근 상품이 없으면 최근 상품을 세팅하지 않는다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns emptyList()
        val slot = slot<List<ProductsItemType>>()
        every { view.setProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()

        // then
        val capturedProducts = slot.captured
        assertTrue(capturedProducts.size == 11)
        verify(exactly = 1) { view.setProducts(capturedProducts) }
    }

    @Test
    fun `리스트에 있는 상품을 클릭하면 상세화면으로 이동한다`() {
        // given
        every { productRepository.findById(any()) } returns fakeProduct
        every { recentRepository.getRecent(10) } returns List(10) { fakeRecentProduct }

        // when
        presenter.setUpProducts()
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `더 보기를 누르면 상품을 불러온다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeRecentProduct }
        val slot = slot<List<ProductsItemType>>()
        every { view.addProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()
        presenter.fetchMoreProducts()

        // then

        val capturedProducts = slot.captured
        assertTrue(capturedProducts.size == 22)
        verify(exactly = 1) { view.addProducts(capturedProducts) }
    }
}
