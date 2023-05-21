package woowacourse.shopping.ui.detailedProduct

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository
import woowacourse.shopping.utils.SharedPreferenceUtils

class DetailedProductPresenterTest {

    private lateinit var view: DetailedProductContract.View
    private lateinit var presenter: DetailedProductContract.Presenter
    private lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
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
        view = mockk()
        sharedPreferenceUtils = mockk()
        productRepository = mockk()
        cartRepository = mockk()
        recentRepository = mockk()
        presenter =
            DetailedProductPresenter(
                view,
                fakeProduct.toUIModel(),
                sharedPreferenceUtils,
                productRepository,
                cartRepository,
                recentRepository
            )
    }

    @Test
    fun `마지막으로 본 상품을 세팅한다`() {
        // given
        every { sharedPreferenceUtils.getLastProductId() } returns fakeProduct.id
        every { sharedPreferenceUtils.setLastProductId(any()) } answers { nothing }

        // when
        presenter.setUpLastProduct()

        // then
        verify(exactly = 1) { sharedPreferenceUtils.getLastProductId() }
        verify(exactly = 1) { sharedPreferenceUtils.setLastProductId(any()) }
    }

    @Test
    fun `상품을 불러와서 세팅한다`() {
        // given
        every { view.setProductDetail(any(), any()) } answers { nothing }

        // when
        presenter.setUpProductDetail()

        // then
        verify(exactly = 1) { view.setProductDetail(any(), any()) }
    }

    @Test
    fun `상품을 장바구니에 추가한다`() {
        // given
        every { cartRepository.insert(any()) } answers { nothing }
        every { productRepository.findById(any()) } answers { fakeProduct }
        every { cartRepository.updateCount(any(), any()) } returns 0
        every { view.navigateToCart() } answers { nothing }

        // when
        presenter.addProductToCart(fakeProduct.id)

        // then
        verify(exactly = 1) { cartRepository.updateCount(fakeProduct.id, any()) }
        verify(exactly = 1) { view.navigateToCart() }
    }

    @Test
    fun `상품을 최근 본 상품에 추가한다`() {
        // given
        every { recentRepository.findById(any()) } answers { fakeRecentProduct }
        every { recentRepository.insert(any()) } answers { nothing }
        every { recentRepository.delete(any()) } answers { nothing }

        // when
        presenter.addProductToRecent()

        // then
        verify(exactly = 1) { recentRepository.insert(fakeProduct) }
        verify(exactly = 1) { recentRepository.findById(fakeProduct.id) }
        verify(exactly = 1) { recentRepository.delete(fakeProduct.id) }
    }

    @Test
    fun `최근 본 상품으로 이동한다`() {
        // given
        every { view.navigateToDetailedProduct(any()) } answers { nothing }
        every { sharedPreferenceUtils.getLastProductId() } returns 4
        every { productRepository.findById(any()) } returns fakeProduct
        every { sharedPreferenceUtils.setLastProductId(any()) } answers { nothing }

        // when
        presenter.setUpLastProduct()
        presenter.navigateToDetailedProduct()

        // then
        verify(exactly = 1) { view.navigateToDetailedProduct(any()) }
        verify(exactly = 2) { sharedPreferenceUtils.setLastProductId(any()) }
    }

    @Test
    fun `장바구니에 상품을 추가하는 다이얼로그로 이동한다`() {
        // given
        every { view.navigateToAddToCartDialog(any()) } answers { nothing }
        every { cartRepository.insert(any()) } answers { nothing }

        // when
        presenter.navigateToAddToCartDialog()

        // then
        verify(exactly = 1) { view.navigateToAddToCartDialog(any()) }
        verify(exactly = 1) { cartRepository.insert(any()) }
    }
}
