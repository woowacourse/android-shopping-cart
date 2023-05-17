package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingPresenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUp() {
        view = mockk()
        productRepository = mockk()
        recentProductRepository = mockk()

        every {
            productRepository.initMockData()
        } just runs

        every {
            recentProductRepository.selectAll()
        } returns RecentProducts(emptyList())

        every {
            productRepository.selectByRange(any(), any())
        } returns CartProducts(emptyList())

        every {
            view.addProducts(any())
        } just runs

        presenter = ShoppingPresenter(
            view,
            productRepository = productRepository,
            recentProductRepository = recentProductRepository,
            recentProductSize = 0,
            productLoadSize = 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given

        // when

        // then
        verify {
            productRepository.initMockData()
        }

        verify {
            view.addProducts(CartProducts(emptyList()).value.map { it.toViewModel() })
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        val productModel = ProductModel("", "", 0)

        every {
            recentProductRepository.insertRecentProduct(any())
            view.showProductDetail(any())
        } just runs

        // when
        presenter.openProduct(productModel)

        // then
        verify {
            recentProductRepository.insertRecentProduct(any())
            view.showProductDetail(productModel)
        }
    }

    @Test
    fun 카트를_열면_카트_뷰를_보여준다() {
        // given
        every { view.showCart() } just runs

        // when
        presenter.openCart()

        // then
        verify { view.showCart() }
    }

    @Test
    fun 새로운_상품을_불러오고_갱신한다() {
        // given

        // when
        presenter.loadMoreProduct()

        // then
        verify {
            productRepository.selectByRange(0, 0)
            view.addProducts(CartProducts(emptyList()).value.map { it.toViewModel() })
        }
    }
}
