package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productdetail.ProductDetailContract
import woowacourse.shopping.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val products = mutableListOf(
        CartProduct(
            0,
            1,
        ),
        CartProduct(
            0,
            1,
        ),
        CartProduct(
            0,
            1,
        ),
        CartProduct(
            0,
            1,
        ),
    )

    private val recentViewedRepository = object : RecentViewedRepository {
        private val mIds = mutableListOf(0, 1, 2)
        override fun findAll(): List<Int> {
            return mIds.toList()
        }

        override fun add(id: Int) {
            mIds.add(id)
        }

        override fun remove(id: Int) {
            mIds.find { it == id }?.let {
                mIds.remove(it)
            }
        }
    }

    private val cartRepository = object : CartRepository {
        private val mProducts = products
        override fun findAll(): List<CartProduct> {
            return mProducts
        }

        override fun find(id: Int): CartProduct? {
            return mProducts.find { it.id == id }
        }

        override fun add(id: Int, count: Int) {
            mProducts.add(CartProduct(id, count))
        }

        override fun update(id: Int, count: Int) {
            val index = mProducts.indexOfFirst { it.id == id }
            if (index == -1) {
                mProducts.add(CartProduct(id, count))
                return
            }
            mProducts[index] = CartProduct(id, count)
        }

        override fun remove(id: Int) {
            mProducts.filter { it.id != id }.toList()
        }

        override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
            return mProducts.subList(mark, mark + rangeSize)
        }

        override fun isExistByMark(mark: Int): Boolean {
            return mProducts.find { it.id == mark } != null
        }
    }

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = ProductDetailPresenter(1, view, cartRepository, recentViewedRepository)
    }

    @Test
    fun `장바구니 담기 버튼을 클릭하면 장바구니에 상품이 담긴다`() {
        val product = ProductModel(
            10,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            10000,
            10
        )
        presenter.putInCart(product)
        val expectedSize = 5
        val actualSize = cartRepository.findAll().size

        assertEquals(expectedSize, actualSize)
        verify { view.finishActivity(true) }
    }

    @Test
    fun `상품 상세 페이지로 들어가면 최근 본 상품에 해당 상품이 등록된다`() {
        val id = 1
        presenter.updateRecentViewedProducts(id)

        val expectedSize = 4
        val actualSize = recentViewedRepository.findAll().size

        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun `현재 선택한 개수가 1이상 100 미만의 값이라면 개수 증가를 할 수 있다`() {
        presenter = ProductDetailPresenter(3, view, cartRepository, recentViewedRepository)
        presenter.plusCount()

        val expectedCount = 4
        val actualCount = presenter.count.value

        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun `현재 선택한 개수가 100이라면 개수 증가를 할 수 없다`() {
        presenter = ProductDetailPresenter(100, view, cartRepository, recentViewedRepository)
        presenter.plusCount()

        val expectedCount = 100
        val actualCount = presenter.count.value

        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun `현재 선택한 개수가 1이라면 개수 감소를 할 수 없다`() {
        presenter = ProductDetailPresenter(1, view, cartRepository, recentViewedRepository)
        presenter.minusCount()

        val expectedCount = 1
        val actualCount = presenter.count.value

        assertEquals(expectedCount, actualCount)
    }
}
