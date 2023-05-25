package woowacourse.shopping.ui.productdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.productdetail.uistate.LastViewedProductUIState
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import java.time.LocalDateTime

class ProductDetailPresenterTest {

    private lateinit var view: FakeView
    private lateinit var productRepository: ProductRepository
    private lateinit var cartItemRepository: CartItemRepository
    private lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
    private lateinit var sut: ProductDetailPresenter
    private val dummyProduct = Product(1L, "dummy", "dummy", 20_000)

    @BeforeEach
    fun setUp() {
        view = FakeView()
        productRepository = mockk()
        cartItemRepository = mockk()
        recentlyViewedProductRepository = mockk()
        sut = ProductDetailPresenter(
            view,
            productRepository,
            cartItemRepository,
            recentlyViewedProductRepository
        )
    }

    @Test
    fun `특정 상품을 로드하면 뷰에 상품을 보여주고 최근 본 상품 저장소에 그 상품을 추가한다`() {
        val productId = 1L
        every { productRepository.findById(productId) } returns dummyProduct
        val recentlyViewedProduct = RecentlyViewedProduct(dummyProduct, LocalDateTime.now())
        every { cartItemRepository.existByProductId(productId) } returns false
        every { recentlyViewedProductRepository.save(recentlyViewedProduct) } just runs

        sut.onLoadProduct(productId)

        val productDetailUIState = ProductDetailUIState.create(dummyProduct, false)
        view.productUIState = productDetailUIState
        verify { recentlyViewedProductRepository.save(recentlyViewedProduct) }
    }

    @Test
    fun `특정 상품을 장바구니에 추가할 때 개수가 0개 이하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            sut.onAddProductToCart(1L, 0)
        }.withMessage("장바구니에 상품을 추가하려면 개수는 1개 이상이어야 합니다.")
    }

    @Test
    fun `특정 상품을 장바구니에 추가하면 장바구니 아이템 저장소에 그 상품을 저장한다`() {
        val productId = 1L
        every { productRepository.findById(productId) } returns dummyProduct
        val cartItem = CartItem(dummyProduct, LocalDateTime.now(), 1)
        every { cartItemRepository.save(cartItem) } just runs

        sut.onAddProductToCart(productId, 1)

        verify { cartItemRepository.save(cartItem) }
    }

    @Test
    fun `마지막으로 본 상품을 로드했을 때 최근 본 상품의 개수가 1개 이하라면 최근 본 상품을 보여주지 않는다`() {
        val recentlyViewedProduct = RecentlyViewedProduct(dummyProduct, LocalDateTime.now())
        every {
            recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc()
        } returns listOf(recentlyViewedProduct)

        sut.onLoadLastViewedProduct()

        assertThat(view.lastViewedProductUIState).isNull()
    }

    @Test
    fun `마지막으로 본 상품을 로드했을 때 최근 본 상품의 개수가 2개 이상이라면 최근 본 상품을 보여준다`() {
        val products = (1..11).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val recentlyViewedProducts = products.map {
            RecentlyViewedProduct(it, LocalDateTime.now())
        }
        every {
            recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc()
        } returns recentlyViewedProducts.take(10)

        sut.onLoadLastViewedProduct()

        val lastViewedProductUIState =
            LastViewedProductUIState.from(recentlyViewedProducts[1])
        assertThat(view.lastViewedProductUIState).isEqualTo(lastViewedProductUIState)
    }

    class FakeView : ProductDetailContract.View {

        var productUIState: ProductDetailUIState? = null

        var lastViewedProductUIState: LastViewedProductUIState? = null

        override fun setProduct(product: ProductDetailUIState) {
            this.productUIState = product
        }

        override fun setLastViewedProduct(product: LastViewedProductUIState?) {
            this.lastViewedProductUIState = product
        }

    }
}