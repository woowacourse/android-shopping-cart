package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository

class ShoppingPresenterTest {

    private lateinit var view: ShoppingContract.View
    private lateinit var presenter: ShoppingContract.Presenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository

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
        cartRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, productRepository, recentRepository, cartRepository)
    }

    @Test
    fun `초기 상품들을 세팅한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeRecentProduct }
        every { cartRepository.getAll() } returns CartProducts(emptyList())

        // when
        presenter.setUpProducts()

        // then
        verify(exactly = 1) {
            view.setProducts(
                List(10) { fakeProduct.toUIModel() },
                List(10) { fakeRecentProduct.toUIModel() },
                emptyList()
            )
        }
    }

    @Test
    fun `상품들을 추가로 전달한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }

        // when
        presenter.setUpProducts()
        presenter.addMoreProducts()

        // then
        verify(exactly = 1) { view.addMoreProducts(List(10) { fakeProduct.toUIModel() }) }
    }

    @Test
    fun `상품 관련 정보들을 업데이트 한다`() {
        // given
        every { recentRepository.getRecent(10) } returns List(10) { fakeRecentProduct }
        every { cartRepository.getAll() } returns CartProducts(emptyList())

        // when
        presenter.setUpProducts()
        presenter.updateProducts()

        // then
        verify(exactly = 1) {
            view.refreshProducts(
                List(10) { fakeRecentProduct.toUIModel() },
                emptyList()
            )
        }
    }

    @Test
    fun `상품의 수량을 업데이트 한다`() {
        // given
        every { cartRepository.updateCount(any(), any()) } returns 10
        every { cartRepository.getAll() } returns CartProducts(emptyList())

        // when
        presenter.setUpProducts()
        val count = presenter.updateItem(1, 10)

        // then
        assertEquals(10, count)
        verify(exactly = 1) { view.updateToolbar(0) }
    }

    @Test
    fun `선택한 상품의 상세페이지로 이동한다`() {
        // given
        every { productRepository.findById(any()) } returns fakeProduct

        // when
        presenter.setUpProducts()
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(fakeProduct.toUIModel()) }
    }
}
