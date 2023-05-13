package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.data.dao.ProductDao
import woowacourse.shopping.common.data.dao.RecentProductDao
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
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
        view = mockk()
        productDao = mockk()
        productsState = mockk()
        recentProductDao = mockk()
        recentProductsState = mockk()

        every {
            productDao.initMockData()
            recentProductsState.save(any())
        } just runs

        every {
            recentProductDao.selectAll()
        } returns RecentProducts(emptyList())

        every {
            productsState.load()
        } returns Products(emptyList())

        every {
            productDao.selectByRange(any(), any())
        } returns Products(emptyList())

        every {
            productsState.save(any())
            view.addProducts(any())
        } just runs

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

        // when

        // then
        verify {
            productDao.initMockData()
            recentProductDao.selectAll()
            recentProductsState.save(RecentProducts(emptyList()))
        }

        verify {
            productsState.save(Products(emptyList()))
            view.addProducts(Products(emptyList()).value.map { it.toView() })
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        val productModel = ProductModel("", "", 0)

        every {
            recentProductsState.load()
        } returns RecentProducts(emptyList())

        every {
            recentProductsState.save(any())
            recentProductDao.insertRecentProduct(any())
            view.showProductDetail(any())
        } just runs

        // when
        presenter.openProduct(productModel)

        // then
        verify {
            recentProductsState.save(any())
            recentProductDao.insertRecentProduct(any())
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
            productsState.load()
            productDao.selectByRange(0, 0)
            productsState.save(Products(emptyList()))
            view.addProducts(Products(emptyList()).value.map { it.toView() })
        }
    }
}
