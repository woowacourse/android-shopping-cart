package woowacourse.shopping.presentation.productlist

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.data.model.RecentProductEntity
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.CartFixture
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.productlist.ProductContract
import woowacourse.shopping.presentation.view.productlist.ProductListPresenter

class ProductListPresenterTest {
    private lateinit var presenter: ProductContract.Presenter
    private lateinit var view: ProductContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk()
        cartRepository = mockk(relaxed = true)
        recentProductRepository = mockk()

        presenter =
            ProductListPresenter(view, productRepository, cartRepository, recentProductRepository)
    }

    @Test
    fun `데이터를 받아와 상품 목록 어댑터를 설정한다`() {
        // given
        every { productRepository.loadData(0) } returns dummyData
        val slot = slot<List<ProductModel>>()
        justRun { view.setProductItemsView(capture(slot)) }

        // when
        presenter.loadProductItems()

        // then
        val actual = slot.captured
        val expected = dummyData.map { it.toUIModel() }

        assertEquals(expected, actual)
        verify { productRepository.loadData(0) }
        verify { view.setProductItemsView(actual) }
    }

    @Test
    fun `데이터를 받아와 최근 본 상품을 어댑터를 설정한다`() {
        // given
        every { recentProductRepository.getRecentProducts(10) } returns dummyRecentProduct
        val slot = slot<List<RecentProductModel>>()
        justRun { view.setRecentProductItemsView(capture(slot)) }

        // when
        presenter.loadRecentProductItems()

        // then
        verify { recentProductRepository.getRecentProducts(10) }
        verify { view.setRecentProductItemsView(slot.captured) }
    }

    @Test
    fun `업데이트 된 데이터를 받아와 최근 본 상품을 갱신한다`() {
        // given
        every { recentProductRepository.getRecentProducts(10) } returns dummyRecentProduct
        val slot = slot<List<RecentProductModel>>()
        justRun { view.updateRecentProductItemsView(capture(slot)) }
        presenter.loadRecentProductItems()

        // when
        presenter.updateRecentProductItems()

        // then
        verify { recentProductRepository.getRecentProducts(10) }
        verify { view.updateRecentProductItemsView(slot.captured) }
    }

    @Test
    fun `데이터가 더 존재한다면 추가 데이터를 가져와 갱신한다`() {
        // given
        every { productRepository.loadData(0) } returns dummyData
        val slot = slot<List<ProductModel>>()
        justRun { view.setProductItemsView(capture(slot)) }

        // when
        presenter.loadProductItems()

        // then
        val actual = slot.captured
        val expected = dummyData.map { it.toUIModel() }
        assertEquals(expected, actual)
        verify { productRepository.loadData(0) }
        verify { view.setProductItemsView(actual) }
    }

    @Test
    fun `Cart 옵션을 누르면 장바구니 화면을 보여준다`() {
        // given
        justRun { view.moveToCartView() }

        // when
        presenter.actionOptionItem()

        // then
        verify { view.moveToCartView() }
    }

    @Test
    fun `최근 본 상품의 스크롤 위치를 저장한다`() {
        presenter.updateRecentProductsLastScroll(100)
        val actual = presenter.getRecentProductsLastScroll()
        val expected = 100
        assertEquals(expected, actual)
    }

    @Test
    fun `상품의 개수가 추가 된 상품들은 장바구니에 추가된다`() {
        // given
        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
        justRun { view.updateToolbarCartCountView(2) }
        justRun { view.setVisibleToolbarCartCountView() }

        // when
        presenter.updateCount(1L, 1)

        // then
        verify { cartRepository.getAllCarts() }
        verify { view.updateToolbarCartCountView(2) }
        verify { view.setVisibleToolbarCartCountView() }
    }

    companion object {
        private val dummyData = listOf(
            ProductEntity(
                id = 0,
                title = "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                price = 24_900,
                imageUrl = "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            )
        )

        private val dummyRecentProduct = listOf(
            RecentProductEntity(
                id = 0L,
                productId = 0L,
            )
        )
    }
}
