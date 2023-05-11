package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.data.database.dao.ProductDao
import woowacourse.shopping.common.data.database.dao.RecentProductDao
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenterTest {
    private lateinit var presenter: ShoppingPresenter
    private lateinit var view: ShoppingContract.View
    private lateinit var productDao: ProductDao
    private lateinit var productsState: State<Products>
    private lateinit var recentProductsState: State<RecentProducts>
    private lateinit var recentProductDao: RecentProductDao

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productDao = mockk(relaxed = true)
        productsState = mockk(relaxed = true)
        recentProductDao = mockk(relaxed = true)
        recentProductsState = mockk(relaxed = true)

        every {
            productsState.load()
        } returns Products(emptyList())

        every {
            productDao.selectByRange(any(), any())
        } returns Products(emptyList())

        every {
            recentProductsState.load()
        } returns RecentProducts(emptyList())

        presenter = ShoppingPresenter(
            view,
            productDao = productDao,
            productsState = productsState,
            recentProductDao = recentProductDao,
            recentProductsState = recentProductsState,
            recentProductSize = 0,
            productLoadSize = 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given
        justRun {
            productsState.save(any())
            view.addProducts(any())
        }

        // when

        // then
        verify {
            productsState.save(any())
            view.addProducts(any())
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        justRun {
            recentProductsState.save(any())
            recentProductDao.insertRecentProduct(any())
            view.showProductDetail(any())
        }

        // when
        presenter.openProduct(mockk(relaxed = true))

        // then
        verify {
            recentProductsState.save(any())
            recentProductDao.insertRecentProduct(any())
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
            productsState.save(any())
            view.addProducts(any())
        }

        // when
        presenter.loadMoreProduct()

        // then
        verify {
            productDao.selectByRange(any(), any())
            productsState.save(any())
            view.addProducts(any())
        }
    }
}
