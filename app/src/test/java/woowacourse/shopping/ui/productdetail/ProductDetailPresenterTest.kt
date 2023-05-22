package woowacourse.shopping.ui.productdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import java.time.LocalDateTime

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var cartItemRepository: CartItemRepository
    private lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
    private lateinit var sut: ProductDetailPresenter
    private val dummyProduct = Product(1L, "dummy", "dummy", 20_000)

    @BeforeEach
    fun setUp() {
        view = mockk()
        productRepository = mockk()
        cartItemRepository = mockk(relaxed = true)
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
        every { view.setProduct(ProductDetailUIState.create(dummyProduct, false)) } just runs
        val recentlyViewedProduct = RecentlyViewedProduct(dummyProduct, LocalDateTime.now())
        every { recentlyViewedProductRepository.save(recentlyViewedProduct) } just runs

        sut.onLoadProduct(productId)

        verify { view.setProduct(ProductDetailUIState.create(dummyProduct, false)) }
        verify { recentlyViewedProductRepository.save(recentlyViewedProduct) }
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
}