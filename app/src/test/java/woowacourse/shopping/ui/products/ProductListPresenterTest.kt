package woowacourse.shopping.ui.products

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState
import java.time.LocalDateTime

class ProductListPresenterTest {

    private lateinit var view: FakeView
    private lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var cartItemRepository: CartItemRepository
    private lateinit var sut: ProductListPresenter
    private val dummyProduct = Product(1L, "www.naver.com", "네이버", 20_000)
    private val dummyRecentlyViewedProduct =
        RecentlyViewedProduct(dummyProduct, LocalDateTime.MAX).apply { id = 1 }

    @BeforeEach
    fun setUp() {
        view = FakeView()
        recentlyViewedProductRepository = mockk()
        productRepository = mockk()
        cartItemRepository = mockk()
        sut = ProductListPresenter(
            view,
            recentlyViewedProductRepository,
            productRepository,
            cartItemRepository
        )
    }

    @Test
    fun `현재 페이지를 요청하면 현재 페이지를 반환한다`() {
        val actual = sut.getCurrentPage()

        assertThat(actual).isZero
    }

    @Test
    fun `현재 페이지를 회복하면 현재 페이지가 변하고 그에 맞는 상품들과 페이지 관련 UI를 보여준다`() {
        val dummyProducts = List(21) { dummyProduct }
        every { productRepository.findAll(any(), any()) } returns dummyProducts
        every { productRepository.countAll() } returns dummyProducts.size
        val productUIStates = dummyProducts.map(ProductUIState::from)

        sut.restoreCurrentPage(2)

        assertThat(sut.getCurrentPage()).isEqualTo(2)
        assertThat(view.productList).isEqualTo(productUIStates)
        assertThat(view.canLoadMoreProducts).isFalse
    }

    @Test
    fun `최근 본 상품들을 로드하면 최근 본 상품들을 보여준다`() {
        every { recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc() } returns listOf(
            dummyRecentlyViewedProduct
        )
        val recentlyViewedProducts =
            listOf(dummyRecentlyViewedProduct).map(RecentlyViewedProductUIState::from)

        sut.onLoadRecentlyViewedProducts()

        assertThat(view.recentlyViewedProductList).isEqualTo(recentlyViewedProducts)
    }

    @Test
    fun `다음 페이지를 로드하면 현재 페이지가 1 증가하고 그에 맞는 상품들과 페이지 관련 UI를 보여준다`() {
        val dummyProducts = (1..20).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        every { productRepository.findAll(20, 0) } returns dummyProducts
        every { cartItemRepository.findByProductId(any()) } returns null
        every { productRepository.countAll() } returns 20

        sut.onLoadProductsNextPage()

        val productUIStates = dummyProducts.map(ProductUIState::from)
        assertThat(view.productList).isEqualTo(productUIStates)
        assertThat(view.canLoadMoreProducts).isFalse
    }

    @Test
    fun `상품 목록을 새로고침하면 상품들을 보여준다`() {
        val dummyProducts = (1..20).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        every { productRepository.findAll(any(), any()) } returns dummyProducts
        every { cartItemRepository.findByProductId(any()) } returns null

        sut.onRefreshProducts()

        val productUIStates = dummyProducts.map(ProductUIState::from)
        assertThat(view.productList).isEqualTo(productUIStates)
    }

    @Test
    fun `상품을 장바구니에 추가하면 뷰에 그 상품을 장바구니에 추가된 상품 UI로 대체하고 장바구니 아이템의 개수 UI를 변경한다`() {
        val productId = 1L
        view.productList.add(ProductUIState.from(dummyProduct))
        every { productRepository.findById(productId) } returns dummyProduct
        val cartItem = CartItem(dummyProduct, LocalDateTime.now(), 1)
        every { cartItemRepository.save(cartItem) } just runs
        every { cartItemRepository.countAll() } returns 1

        sut.onAddToCart(productId)

        val productUIStates = listOf(ProductUIState.from(cartItem))
        assertThat(view.productList).isEqualTo(productUIStates)
        assertThat(view.cartItemSize).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담긴 상품 개수를 플러스하면 그 상품 UI를 개수가 갱신된 상품 UI로 대체한다`() {
        val cartItemId = 1L
        val cartItem = CartItem(dummyProduct, LocalDateTime.now(), 1).apply { id = cartItemId }
        view.productList.add(ProductUIState.from(cartItem))
        every { cartItemRepository.findById(cartItemId) } returns cartItem
        every { cartItemRepository.updateCountById(cartItemId, 2) } just runs

        sut.onPlusCount(cartItemId)

        val productUIStates = listOf(ProductUIState.from(cartItem))
        assertThat(view.productList).isEqualTo(productUIStates)
    }

    @Test
    fun `장바구니에 담긴 상품 개수를 마이너스하면 그 상품이 1개 이상이라면 UI를 개수가 갱신된 상품 UI로 대체한다`() {
        val cartItemId = 1L
        val cartItem = CartItem(dummyProduct, LocalDateTime.now(), 2).apply { id = cartItemId }
        view.productList.add(ProductUIState.from(cartItem))
        every { cartItemRepository.findById(cartItemId) } returns cartItem
        every { cartItemRepository.updateCountById(cartItemId, 1) } just runs

        sut.onMinusCount(cartItemId)

        val productUIStates = listOf(ProductUIState.from(cartItem))
        assertThat(view.productList).isEqualTo(productUIStates)
    }

    @Test
    fun `장바구니에 담긴 상품 개수를 마이너스하면 그 상품이 0개라면 UI를 개수가 없는 상품 UI로 대체하고 장바구니 아이템의 개수 UI를 변경한다`() {
        val cartItemId = 1L
        val cartItem = CartItem(dummyProduct, LocalDateTime.now(), 1).apply { id = cartItemId }
        view.productList.add(ProductUIState.from(cartItem))
        view.cartItemSize = 1
        every { cartItemRepository.findById(cartItemId) } returns cartItem
        every { cartItemRepository.deleteById(cartItemId) } just runs
        every { cartItemRepository.countAll() } returns 0

        sut.onMinusCount(cartItemId)

        val productUIStates = listOf(ProductUIState.from(dummyProduct))
        assertThat(view.productList).isEqualTo(productUIStates)
        assertThat(view.cartItemSize).isZero
    }

    @Test
    fun `장바구니 아이템의 개수를 로드하면 장바구니 아이템의 개수 UI를 변경한다`() {
        val cartItemCount = 1
        every { cartItemRepository.countAll() } returns cartItemCount

        sut.onLoadCartItemCount()

        assertThat(view.cartItemSize).isEqualTo(1)
    }

    class FakeView : ProductListContract.View {

        var recentlyViewedProductList: List<RecentlyViewedProductUIState> = emptyList()

        var productList: MutableList<ProductUIState> = mutableListOf()

        var canLoadMoreProducts: Boolean = false

        var cartItemSize: Int = 0
        override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
            this.recentlyViewedProductList = recentlyViewedProducts
        }

        override fun addProducts(products: List<ProductUIState>) {
            this.productList.addAll(products)
        }

        override fun replaceProduct(product: ProductUIState) {
            val index = this.productList.indexOfFirst { it.id == product.id }
            this.productList[index] = product
        }

        override fun setProducts(products: List<ProductUIState>) {
            this.productList.clear()
            this.productList.addAll(products)
        }

        override fun setCanLoadMore(canLoadMore: Boolean) {
            this.canLoadMoreProducts = canLoadMore
        }

        override fun setCartItemCount(count: Int) {
            this.cartItemSize = count
        }

    }
}