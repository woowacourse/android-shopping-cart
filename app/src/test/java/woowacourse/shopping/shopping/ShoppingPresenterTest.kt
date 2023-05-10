package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.data.database.dao.ProductDao
import woowacourse.shopping.common.data.database.dao.RecentProductDao
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingPresenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productDao: ProductDao
    private lateinit var recentProductDao: RecentProductDao

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productDao = mockk(relaxed = true)
        recentProductDao = mockk(relaxed = true)
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given
        justRun {
            view.updateProductList(any())
            view.updateRecentProductList(any())
        }

        // when
        presenter = ShoppingPresenter(
            view,
            productDao = productDao,
            recentProductDao = recentProductDao,
            recentProductSize = 0
        )

        // then
        verify {
            view.updateProductList(any())
            view.updateRecentProductList(any())
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        val recentProducts: RecentProducts = mockk()
        every { recentProducts.getRecentProducts(any()) } returns RecentProducts(emptyList())
        justRun {
            recentProducts.add(any())
            view.showProductDetail()
        }

        presenter = ShoppingPresenter(
            view,
            productDao = productDao,
            recentProductDao = recentProductDao,
            recentProductSize = 0
        )

        // when
        presenter.openProduct(mockk(relaxed = true))

        // then
        verify {
            recentProducts.add(any())
            view.showProductDetail()
        }
    }

    @Test
    fun 카트를_열면_카트_뷰를_보여준다() {
        // given
        justRun { view.showCart() }
        presenter = ShoppingPresenter(
            view,
            productDao = productDao,
            recentProductDao = recentProductDao,
            recentProductSize = 0
        )

        // when
        presenter.openCart()

        // then
        verify { view.showCart() }
    }
}
