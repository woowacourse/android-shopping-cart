package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.CartPageStatus
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.model.toUiModel
import woowacourse.shopping.view.cart.CartContract
import woowacourse.shopping.view.cart.CartPresenter
import woowacourse.shopping.view.cart.CartViewItem

class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        val productRepository = object : ProductRepository {
            private val mProducts = products
            override fun findAll(): List<Product> {
                return mProducts
            }

            override fun find(id: Int): Product {
                return mProducts[id]
            }

            override fun findRange(mark: Int, rangeSize: Int): List<Product> {
                return mProducts.subList(mark, mark + rangeSize)
            }

            override fun isExistByMark(mark: Int): Boolean {
                return mProducts.find { it.id == mark } != null
            }
        }

        val cartRepository = object : CartRepository {
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

            override fun add(id: Int, count: Int) {
                cartProducts.add(CartProduct(id, count))
            }

            override fun remove(id: Int) {
                cartProducts.remove(cartProducts.find { it.id == id })
            }

            override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
                return cartProducts.subList(mark, mark + rangeSize)
            }

            override fun isExistByMark(mark: Int): Boolean {
                return cartProducts.getOrNull(mark) != null
            }
        }

        presenter = CartPresenter(view, cartRepository, productRepository)
        presenter.fetchProducts()
    }

    @Test
    fun 장바구니의_상품을_띄울_수_있다() {
        val items = slot<List<CartViewItem>>()
        every { view.showProducts(capture(items)) } just runs
        presenter.fetchProducts()
        val itemsExpected = listOf<CartViewItem>(
            CartViewItem.CartProductItem(CartProduct(0, 1).toUiModel(products[0])),
            CartViewItem.CartProductItem(CartProduct(1, 1).toUiModel(products[1])),
            CartViewItem.CartProductItem(CartProduct(2, 1).toUiModel(products[2])),
            CartViewItem.CartProductItem(CartProduct(3, 1).toUiModel(products[3])),
            CartViewItem.CartProductItem(CartProduct(4, 1).toUiModel(products[4])),
        ) + CartViewItem.PaginationItem(
            CartPageStatus(
                isPrevEnabled = false,
                isNextEnabled = true,
                count = 1
            )
        )
        assertEquals(itemsExpected, items.captured)
    }

    @Test
    fun 장바구니_상품을_삭제할_수_있다() {
        val actualId = slot<Int>()
        every { view.notifyRemoveItem(capture(actualId)) } just runs
        presenter.removeProduct(0)
        assertEquals(0, actualId.captured)
    }

    private val products = listOf(
        Product(0, "락토핏", "", Price(10000)),
        Product(1, "락토핏", "", Price(10000)),
        Product(2, "락토핏", "", Price(10000)),
        Product(3, "락토핏", "", Price(10000)),
        Product(4, "락토핏", "", Price(10000)),
        Product(5, "락토핏", "", Price(10000)),
        Product(6, "락토핏", "", Price(10000)),
        Product(7, "락토핏", "", Price(10000)),
        Product(8, "락토핏", "", Price(10000)),
        Product(9, "락토핏", "", Price(10000)),
        Product(10, "락토핏", "", Price(10000)),
        Product(11, "락토핏", "", Price(10000)),
        Product(12, "락토핏", "", Price(10000)),
        Product(13, "락토핏", "", Price(10000)),
        Product(14, "락토핏", "", Price(10000)),
        Product(15, "락토핏", "", Price(10000)),
        Product(16, "락토핏", "", Price(10000)),
        Product(17, "락토핏", "", Price(10000)),
        Product(18, "락토핏", "", Price(10000)),
        Product(19, "락토핏", "", Price(10000)),
        Product(20, "락토핏", "", Price(10000)),
        Product(21, "락토핏", "", Price(10000)),
    )
}
