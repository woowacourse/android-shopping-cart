package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingPresenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        recentProductRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)

        every {
            productRepository.getProducts(any(), any())
        } returns Products(emptyList())

        every {
            recentProductRepository.addRecentProduct(any())
        } just runs

        every {
            recentProductRepository.getAll()
        } returns RecentProducts()

        every {
            recentProductRepository.getByProduct(any())
        } returns null

        every {
            recentProductRepository.modifyRecentProduct(any())
        } just runs

        every {
            cartRepository.getTotalAmount()
        } returns 0

        presenter = ShoppingPresenter(
            view,
            productRepository = productRepository,
            recentProductRepository = recentProductRepository,
            cartRepository = cartRepository,
            recentProductSize = 0,
            productLoadSize = 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given
        justRun {
            view.addProducts(any())
        }

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

        // when
        presenter.setUpCartAmount()

        // then
        verify {
            view.updateCartAmount(any())
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        justRun {
            view.showProductDetail(any())
        }

        // when
        presenter.openProduct(mockk(relaxed = true))

        // then
        verify {
            recentProductRepository.addRecentProduct(any())
            view.showProductDetail(any())
        }
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
}
