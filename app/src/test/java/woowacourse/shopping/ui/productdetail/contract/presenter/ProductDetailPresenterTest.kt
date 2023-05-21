package woowacourse.shopping.ui.productdetail.contract.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import com.example.domain.repository.RecentRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract
import kotlin.properties.Delegates

internal class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var cartRepository: CartRepository
    private lateinit var recentRepository: RecentRepository
    private var visible by Delegates.notNull<Boolean>()

    private val fakeProduct: Product = Product(
        1,
        "[사미헌] 갈비탕",
        12000,
        "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg",
    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        visible = true
        cartRepository = mockk(relaxed = true)
        recentRepository = mockk(relaxed = true)
        presenter =
            ProductDetailPresenter(
                view,
                fakeProduct.toUIModel(),
                visible,
                cartRepository,
                recentRepository,
            )
    }

    @Test
    fun `상품을 불러와서 세팅한다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { view.setProductDetail(capture(slot)) } answers { nothing }
        // when
        presenter.setUpProductDetail()

        // then
        Assert.assertEquals(slot.captured, fakeProduct.toUIModel())
        verify { view.setProductDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `상품을 장바구니에 추가한다`() {
        // given
        every { cartRepository.insert(any()) } answers { nothing }
        // when
        presenter.addProductToCart()
        // then
        verify(exactly = 1) { cartRepository.insert(CartProduct(fakeProduct, 1, true)) }
    }

    @Test
    fun `상품을 최근 본 상품에 추가한다`() {
        // given
        every { recentRepository.insert(any()) } answers { nothing }
        // when
        presenter.addProductToRecent()
        // then
        verify { recentRepository.insert(fakeProduct) }
    }

    @Test
    fun `최근 본 상품을 볼 수 있는지 확인`() {
        // given
        val slot = slot<Boolean>()
        every { view.setVisibleLatestProduct(capture(slot)) } answers { nothing }
        // when
        presenter.isVisibleLatestProduct()
        // then
        assertEquals(slot.captured, visible)
        verify { view.setVisibleLatestProduct(slot.captured) }
    }

    @Test
    fun `다이얼로그를 세팅한다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { view.showProductCountDialog(capture(slot)) } answers { nothing }
        // when
        presenter.setProductCountDialog()
        // then
        assertEquals(slot.captured, fakeProduct.toUIModel())
        verify { view.showProductCountDialog(fakeProduct.toUIModel()) }
    }

    @Test
    fun `마지막으로 본 상품을 보여준다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { recentRepository.getRecent(1) } returns listOf(fakeProduct)
        every { view.showLatestProduct(capture(slot)) } answers { nothing }
        // when
        presenter.setLatestProduct()
        // then
        assertEquals(slot.captured, fakeProduct.toUIModel())
        verify { view.showLatestProduct(fakeProduct.toUIModel()) }
    }

    @Test
    fun `마지막으로 본 상품을 누르면 상세화면으로 이동한다`() {
        // given
        val slot = slot<ProductUIModel>()
        every { recentRepository.getRecent(1) } returns listOf(fakeProduct)
        every { view.navigateToDetail(capture(slot)) } answers { nothing }
        // when
        presenter.setLatestProduct()
        presenter.clickLatestProduct()
        // then
        assertEquals(slot.captured, fakeProduct.toUIModel())
        verify { view.navigateToDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `상품 수량을 증가시킨다`() {
        // given
        presenter.addProductCount(fakeProduct.id)
        // then
        assertEquals(presenter.count.value, 2)
    }

    @Test
    fun `상품 수량을 감소시킨다`() {
        // given
        presenter.addProductCount(fakeProduct.id)
        presenter.subtractProductCount(fakeProduct.id)
        // then
        assertEquals(presenter.count.value, 1)
    }
}
