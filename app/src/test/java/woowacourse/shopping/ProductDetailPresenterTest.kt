package woowacourse.shopping

import com.example.domain.model.CartRepository
import com.example.domain.model.Product
import com.example.domain.model.RecentRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract
import woowacourse.shopping.ui.productdetail.contract.presenter.ProductDetailPresenter

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var recentRepository: RecentRepository

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentRepository = mockk(relaxed = true)
        presenter =
            ProductDetailPresenter(view, fakeProduct.toUIModel(), cartRepository, recentRepository)
    }

    @Test
    fun `상품을 불러와서 세팅한다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { view.setProductDetail(capture(slot)) } answers { nothing }
        // when
        presenter.setUpProductDetail()

        // then
        assertEquals(slot.captured, fakeProduct.toUIModel())
        verify(exactly = 1) { view.setProductDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `상품을 장바구니에 추가한다`() {
        // given
        every { cartRepository.insert(any()) } answers { nothing }
        // when
        presenter.addProductToCart()
        // then
        verify(exactly = 1) { cartRepository.insert(fakeProduct) }
    }

    @Test
    fun `상품을 최근 본 상품에 추가한다`() {
        // given
        every { recentRepository.insert(any()) } answers { nothing }
        // when
        presenter.addProductToRecent()
        // then
        verify(exactly = 1) { recentRepository.insert(fakeProduct) }
    }
}
