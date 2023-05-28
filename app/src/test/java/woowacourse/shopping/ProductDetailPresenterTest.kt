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

    private val product = ProductModel(
        10,
        "락토핏",
        "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
        10000,
        10,
    )
    private val lastViewedProduct = ProductModel(
        11,
        "락토핏11",
        "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
        10000,
        10,
    )

    private val recentViewedRepository = object : RecentViewedRepository {
        override fun findAll(): List<Int> {
            return listOf(0, 1, 2)
        }

        override fun add(id: Int) {
        }

        override fun remove(id: Int) {
        }
    }

    private val cartRepository = object : CartRepository {
        private val cartProducts = mutableListOf<CartProduct>()

        init {
            cartProducts.add(CartProduct(0, 1))
            cartProducts.add(CartProduct(1, 1))
            cartProducts.add(CartProduct(2, 1))
            cartProducts.add(CartProduct(3, 1))
            cartProducts.add(CartProduct(4, 1))
            cartProducts.add(CartProduct(5, 1))
            cartProducts.add(CartProduct(6, 1))
            cartProducts.add(CartProduct(7, 1))
        }

        override fun findAll(): List<CartProduct> {
            return cartProducts
        }

        override fun find(id: Int): CartProduct? {
            return CartProduct(0, 1)
        }

        override fun add(id: Int, count: Int) {
            cartProducts.add(CartProduct(id, count))
        }

        override fun update(id: Int, count: Int) {
        }

        override fun remove(id: Int) {
        }

        override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
            return listOf(
                CartProduct(0, 1),
                CartProduct(1, 1),
                CartProduct(2, 1),
                CartProduct(3, 1),
                CartProduct(4, 1),
            )
        }

        override fun isExistByMark(mark: Int): Boolean {
            if (mark < 0) return false
            return true
        }
    }

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = ProductDetailPresenter(1, product, lastViewedProduct, view, cartRepository, recentViewedRepository)
    }

    @Test
    fun `장바구니 담기 버튼을 클릭하면 장바구니에 상품이 담긴다`() {
        val product = ProductModel(
            0,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            10000,
            10,
        )
        presenter.putInCart(product)
        val expectedSize = 9
        val actualSize = cartRepository.findAll().size

        assertEquals(expectedSize, actualSize)
        verify { view.finishActivity(true) }
    }

    @Test
    fun `현재 선택한 개수가 1이상의 값이라면 개수 증가를 할 수 있다`() {
        presenter = ProductDetailPresenter(3, product, lastViewedProduct, view, cartRepository, recentViewedRepository)
        presenter.plusCount()

        val expectedCount = 4
        val actualCount = presenter.count.value

        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun `현재 선택한 개수가 1이라면 개수 감소를 할 수 없다`() {
        presenter = ProductDetailPresenter(3, product, lastViewedProduct, view, cartRepository, recentViewedRepository)
        presenter.minusCount()

        val expectedCount = 2
        val actualCount = presenter.count.value

        assertEquals(expectedCount, actualCount)
    }
}
