package woowacourse.shopping.shopping

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.createCartProduct
import woowacourse.shopping.createProductModel
import woowacourse.shopping.createRecentProduct
import woowacourse.shopping.createShoppingProductModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.domain.URL
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingPresenter
    private val view: ShoppingContract.View = mockk()
    private val productRepository: ProductRepository = mockk()
    private val recentProductRepository: RecentProductRepository = mockk()
    private val cartRepository: CartRepository = mockk()

    @Before
    fun setUp() {
        val shoppingProduct = ShoppingProduct(Product(URL(""), "", 1000), 1000)
        every { productRepository.getProducts(any(), any()) } returns Products(
            listOf(
                shoppingProduct
            )
        )
        every { view.addProducts(any()) } just Runs

        presenter = ShoppingPresenter(
            view, productRepository, recentProductRepository, cartRepository, 0, 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given
        every { view.addProducts(any()) } just runs

        // when

        // then
        verify {
            productRepository.getProducts(any(), any())
            view.addProducts(any())
        }
    }

    @Test
    fun 카트_상품_개수를_세팅한다() {
        // given
        every { cartRepository.getTotalAmount() } returns 0
        every { view.updateCartAmount(any()) } just runs

        // when
        presenter.setUpCartAmount()

        // then
        verify {
            view.updateCartAmount(any())
        }
    }

    @Test
    fun 최근_본_상품에_없는_상품을_선택하면_최근_본_상품에_추가한다() {
        // given
        val recentProduct = createRecentProduct()
        every { recentProductRepository.getLatestRecentProduct() } returns recentProduct
        every { recentProductRepository.getAll() } returns RecentProducts(listOf(recentProduct))
        every { recentProductRepository.getByProduct(any()) } returns null
        every { recentProductRepository.addRecentProduct(any()) } just runs
        every { view.showProductDetail(any(), any()) } just runs

        // when
        val productModel = createProductModel()
        presenter.openProduct(productModel)

        // then
        verify { recentProductRepository.addRecentProduct(any()) }
    }

    @Test
    fun 최근_본_상품에_있는_상품을_선택하면_최근_본_상품을_업데이트한다() {
        // given
        val recentProduct = createRecentProduct()
        every { recentProductRepository.getByProduct(any()) } returns recentProduct
        every { recentProductRepository.getLatestRecentProduct() } returns recentProduct
        every { recentProductRepository.getAll() } returns RecentProducts(listOf(recentProduct))
        every { recentProductRepository.modifyRecentProduct(any()) } just runs
        every { view.showProductDetail(any(), any()) } just runs

        // when
        presenter.openProduct(recentProduct.product.toView())

        // then
        verify { recentProductRepository.modifyRecentProduct(any()) }
    }

    @Test
    fun 마지막으로_본_상품이_아닌_상품을_선택하면_마지막으로_본_상품과_함께_상품_상세정보를_보여준다() {
        // given
        val recentProduct = createRecentProduct()
        every { recentProductRepository.getLatestRecentProduct() } returns recentProduct
        every { recentProductRepository.getAll() } returns RecentProducts(listOf(recentProduct))
        every { recentProductRepository.getByProduct(any()) } returns null
        every { recentProductRepository.addRecentProduct(any()) } just runs
        every { view.showProductDetail(any(), any()) } just runs

        // when
        val productModel = createProductModel()
        presenter.openProduct(productModel)

        // then
        verify { view.showProductDetail(any(), recentProduct.product.toView()) }
    }

    @Test
    fun 마지막으로_본_상품을_선택하면_최근_본_상품_없이_상품_상세정보를_보여준다() {
        // given
        val recentProduct = createRecentProduct()
        every { recentProductRepository.getLatestRecentProduct() } returns recentProduct
        every { recentProductRepository.getAll() } returns RecentProducts(listOf(recentProduct))
        every { recentProductRepository.getByProduct(any()) } returns recentProduct
        every { recentProductRepository.modifyRecentProduct(any()) } just runs
        every { view.showProductDetail(any(), any()) } just runs

        // when
        presenter.openProduct(recentProduct.product.toView())

        // then
        verify { view.showProductDetail(any(), isNull()) }
    }

    @Test
    fun 카트를_열면_카트_뷰를_보여준다() {
        // given
        justRun { view.showCart() }

        // when
        presenter.openCart()

        // then
        verify { view.showCart() }
    }

    @Test
    fun 새로운_상품을_불러오고_갱신한다() {
        // given
        justRun {
            view.addProducts(any())
        }

        // when
        presenter.loadMoreProduct()

        // then
        verify {
            productRepository.getProducts(any(), any())
            view.addProducts(any())
        }
    }

    @Test
    fun 카트에_담긴_상품_개수를_증가시키면_카트에_상품이_추가되고_상품과_카트의_총_상품_개수가_업데이트된다() {
        // given
        every { cartRepository.getCartProductByProduct(any()) } returns createCartProduct()
        every { cartRepository.addCartProduct(any()) } just runs
        every { cartRepository.getTotalAmount() } returns 0
        every { view.updateShoppingProduct(any(), any()) } just runs
        every { view.updateCartAmount(any()) } just runs

        // when
        val shoppingProductModel = createShoppingProductModel()
        presenter.increaseCartProductAmount(shoppingProductModel)

        // then
        verify {
            cartRepository.addCartProduct(any())
            view.updateShoppingProduct(any(), any())
            view.updateCartAmount(any())
        }
    }

    @Test
    fun 카트에_담긴_상품_개수를_감소시키면_카트에서_상품이_삭제되고_상품과_카트의_총_상품_개수가_업데이트된다() {
        // given
        every { cartRepository.getCartProductByProduct(any()) } returns createCartProduct()
        every { cartRepository.deleteCartProduct(any()) } just runs
        every { cartRepository.getTotalAmount() } returns 0
        every { view.updateShoppingProduct(any(), any()) } just runs
        every { view.updateCartAmount(any()) } just runs

        // when
        val shoppingProductModel = createShoppingProductModel()
        presenter.decreaseCartProductAmount(shoppingProductModel)

        // then
        verify {
            cartRepository.deleteCartProduct(any())
            view.updateShoppingProduct(any(), any())
            view.updateCartAmount(any())
        }
    }

    @Test
    fun 카트_변경_업데이트를_하면_상품_정보가_업데이트_되고_총_카트_상품_개수가_업데이트_된다() {
        // given
        every { cartRepository.getTotalAmount() } returns 0
        every { view.updateProducts(any()) } just runs
        every { view.updateCartAmount(any()) } just runs

        // when
        presenter.updateCartChange()

        // then
        verify {
            view.updateProducts(any())
            view.updateCartAmount(any())
        }
    }
}
