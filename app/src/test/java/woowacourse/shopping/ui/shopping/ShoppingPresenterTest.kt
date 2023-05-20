package woowacourse.shopping.ui.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProduct
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

    private val fakeCartProduct: CartProduct = CartProduct(
        1,
        "[사미헌] 갈비탕",
        1,
        true,
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
    )

    private val fakeProducts = List(10) { fakeProduct }
    private val fakeRecentProducts = List(10) { fakeRecentProduct }
    private val fakeCartProducts = CartProducts(List(10) { fakeCartProduct })

    private val fakeCartCounts = fakeCartProducts.toUIModel()
        .associateBy { it.id }
        .mapValues { it.value.count }

    @Before
    fun setUp() {
        view = mockk()
        productRepository = mockk()
        recentRepository = mockk()
        cartRepository = mockk()
        presenter = ShoppingPresenter(view, productRepository, recentRepository, cartRepository)
    }

    @Test
    fun `초기 상품들을 세팅한다`() {
        // given
        every { productRepository.getNext(any()) } returns fakeProducts
        every { recentRepository.getRecent(10) } returns fakeRecentProducts
        every { cartRepository.getAll() } returns fakeCartProducts

        every { view.setRecentProducts(any()) } returns Unit
        every { view.addMoreProducts(any()) } returns Unit
        every { view.setCartProducts(any()) } returns Unit

        // when
        presenter.setUpProducts()

        // then
        verify(exactly = 1) {
            view.setRecentProducts(fakeRecentProducts.map { it.toUIModel() })
        }
        verify(exactly = 1) {
            view.addMoreProducts(fakeProducts.map { it.toUIModel() })
        }
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `상품들을 추가로 전달한다`() {
        // given
        every { productRepository.getNext(any()) } returns fakeProducts
        every { view.addMoreProducts(any()) } returns Unit

        // when
        presenter.setUpNextProducts()

        // then
        verify(exactly = 1) { view.addMoreProducts(fakeProducts.map { it.toUIModel() }) }
    }

    @Test
    fun `상품들에 장바구니 숫자를 반영한다`() {
        // given
        every { cartRepository.getAll() } returns fakeCartProducts
        every { view.setCartProducts(any()) } returns Unit

        // when
        presenter.setUpCartCounts()

        // then
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `상품의 개수를 장바구니 저장한다`() {
        // given
        every { productRepository.getNext(any()) } returns fakeProducts
        every { recentRepository.getRecent(10) } returns fakeRecentProducts
        every { cartRepository.getAll() } returns fakeCartProducts

        every { view.setRecentProducts(any()) } returns Unit
        every { view.addMoreProducts(any()) } returns Unit
        every { view.setCartProducts(any()) } returns Unit

        every { cartRepository.insert(any()) } returns Unit
        every { cartRepository.updateCount(1, 10) } returns 10
        every { productRepository.findById(1) } returns fakeProduct
        // when
        presenter.setUpProducts()
        val count = presenter.updateItemCount(1, 10)

        // then
        assertEquals(10, count)
    }

    @Test
    fun `총 장바구니 상품 개수를 반영한다`() {
        // given
        every { cartRepository.getAll() } returns fakeCartProducts
        every { view.setCartProducts(any()) } returns Unit
        every { view.setToolbar(any()) } returns Unit

        // when
        presenter.setUpCartCounts()

        // then
        verify(exactly = 1) {
            view.setCartProducts(fakeCartCounts)
        }
    }

    @Test
    fun `선택한 상품의 상세페이지로 이동한다`() {
        // given
        every { productRepository.findById(any()) } returns fakeProduct
        every { view.navigateToProductDetail(any()) } returns Unit

        // when
        presenter.navigateToItemDetail(fakeProduct.toUIModel().id)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(fakeProduct.toUIModel()) }
    }
}
