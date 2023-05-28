package woowacourse.shopping.ui.shopping.contract.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
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
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.contract.ShoppingContract

internal class ShoppingPresenterTest {

    private lateinit var view: ShoppingContract.View
    private lateinit var presenter: ShoppingPresenter
    private lateinit var productRepository: ProductRepository
    private lateinit var recentRepository: RecentRepository
    private lateinit var cartRepository: CartRepository

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
        productRepository = mockk(relaxed = true)
        recentRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = ShoppingPresenter(view, productRepository, recentRepository, cartRepository)
    }

    @Test
    fun `상품을 불러와서 세팅한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        val slot = slot<List<ProductsItemType>>()
        every { view.setProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()

        // then
        val capturedProducts = slot.captured
        Assert.assertTrue(capturedProducts.size == 12)
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
        Assert.assertTrue(capturedProducts.size == 11)
        verify(exactly = 1) { view.setProducts(capturedProducts) }
    }

    @Test
    fun `상품을 불러와서 업데이트한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        val slot = slot<List<ProductsItemType>>()
        every { view.setProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()
        presenter.updateProducts()

        // then
        val capturedProducts = slot.captured
        Assert.assertTrue(capturedProducts.size == 12)
        verify(exactly = 2) { view.setProducts(capturedProducts) }
    }

    @Test
    fun `리스트에 있는 상품을 클릭하면 상세화면으로 이동한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }

        // when
        presenter.setUpProducts()
        presenter.navigateToItemDetail(fakeProduct.id)

        // then
        verify(exactly = 1) { view.navigateToProductDetail(fakeProduct.toUIModel()) }
    }

    @Test
    fun `수량이 증가한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        // when
        presenter.setUpProducts()
        presenter.increaseCount(fakeProduct.id)

        // then
        assertEquals(1, presenter.countLiveDatas[fakeProduct.id]?.value)
    }

    @Test
    fun `수량이 감소한다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        // when
        presenter.setUpProducts()
        presenter.increaseCount(fakeProduct.id)
        presenter.decreaseCount(fakeProduct.id)

        // then
        assertEquals(0, presenter.countLiveDatas[fakeProduct.id]?.value)
    }

    @Test
    fun `장바구니에 들어있는 상품의 개수를 알 수 있다`() {
        // given
        val slot = slot<Int>()
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        every { cartRepository.getCheckCart() } returns listOf(
            CartProduct(fakeProduct, 3, true),
        )
        every { view.showCountSize(capture(slot)) } answers { nothing }
        // when
        presenter.updateCountSize()

        // then
        assertEquals(1, slot.captured)
        verify(exactly = 1) { view.showCountSize(slot.captured) }
    }

    @Test
    fun `더 보기를 누르면 상품을 불러온다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        val slot = slot<List<ProductsItemType>>()
        every { view.addProducts(capture(slot)) } answers { nothing }

        // when
        presenter.setUpProducts()
        presenter.fetchMoreProducts()

        // then

        val capturedProducts = slot.captured
        Assert.assertTrue(capturedProducts.size == 22)
        verify(exactly = 1) { view.addProducts(capturedProducts) }
    }

    @Test
    fun `모든 아이템들의 수량을 불러온다`() {
        // given
        every { productRepository.getNext(any()) } returns List(10) { fakeProduct }
        every { recentRepository.getRecent(10) } returns List(10) { fakeProduct }
        every { cartRepository.getAll() } returns listOf(
            CartProduct(fakeProduct, 3, true),
        )
        // when
        presenter.setUpProducts()
        presenter.updateItemCounts()

        // then
        assertEquals(3, presenter.countLiveDatas[fakeProduct.id]?.value)
    }
}
