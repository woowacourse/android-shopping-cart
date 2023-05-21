package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.domain.cartsystem.CartPageStatus
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.view.cart.CartContract
import woowacourse.shopping.view.cart.CartPresenter
import woowacourse.shopping.view.cart.CartViewItem

class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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

            override fun find(id: Int): CartProduct? {
                return cartProducts.find { it.id == id }
            }

            override fun add(id: Int, count: Int) {
                cartProducts.add(CartProduct(id, count))
            }

            override fun update(id: Int, count: Int) {
                val index = cartProducts.indexOfFirst { it.id == id }
                if (index == -1) {
                    cartProducts.add(CartProduct(id, count))
                    return
                }
                cartProducts[index] = CartProduct(id, count)
            }

            override fun remove(id: Int) {
                cartProducts.remove(cartProducts.find { it.id == id })
            }

            override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
                return if (mark + rangeSize < cartProducts.size) cartProducts.subList(mark, mark + rangeSize)
                else cartProducts.subList(mark, cartProducts.size)
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
            CartViewItem.CartProductItem(CartProductModel(false, 0, "락토핏", "", 1, 10000)),
            CartViewItem.CartProductItem(CartProductModel(false, 1, "락토핏", "", 1, 10000)),
            CartViewItem.CartProductItem(CartProductModel(false, 2, "락토핏", "", 1, 10000)),
            CartViewItem.CartProductItem(CartProductModel(false, 3, "락토핏", "", 1, 10000)),
            CartViewItem.CartProductItem(CartProductModel(false, 4, "락토핏", "", 1, 10000)),
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
    fun 다음_페이지를_띄울_수_있다() {
        presenter.fetchNextPage()
        verify(exactly = 1) { view.showChangedItems() }
    }

    @Test
    fun 이전_페이지를_띄울_수_있다() {
        presenter.fetchNextPage()
        presenter.fetchPrevPage()
        verify(exactly = 2) { view.showChangedItems() }
    }

    @Test
    fun 장바구니_상품을_삭제할_수_있다() {
        presenter.removeProduct(0)
        verify(exactly = 1) { view.showChangedItems() }
    }

    @Test
    fun 업데이트_하려는_상품의_개수가_1부터_100사이면_업데이트할_수_있다() {
        val actualSlot = slot<Int>()
        every { view.showChangedItem(capture(actualSlot)) } just runs

        presenter.updateCartProductCount(0, 3)
        val expected = 0

        assertEquals(expected, actualSlot.captured)
    }

    @Test
    fun 업데이트_하려는_상품의_개수가_1미만이면_업데이트하지_않는다() {
        val actualSlot = slot<Int>()
        every { view.showChangedItem(capture(actualSlot)) } just runs

        presenter.updateCartProductCount(0, 0)
        val expected = false

        assertEquals(expected, actualSlot.isCaptured)
    }

    @Test
    fun 업데이트_하려는_상품의_개수가_100초과면_업데이트하지_않는다() {
        val actualSlot = slot<Int>()
        every { view.showChangedItem(capture(actualSlot)) } just runs

        presenter.updateCartProductCount(0, 101)
        val expected = false

        assertEquals(expected, actualSlot.isCaptured)
    }

    @Test
    fun 장바구니_상품을_선택하면_가격과_주문개수를_수정한다() {
        presenter.checkProduct(CartProductModel(false, 0, "락토핏", "", 1, 10000))
        val expectedTotalPrice = 10000
        val expectedTotalCount = 1
        val expectedTotalCheckAll = false

        val actualTotalPrice = presenter.cartSystemResult.value?.totalPrice
        val actualTotalCount = presenter.cartSystemResult.value?.totalCount
        val actualTotalCheckAll = presenter.isCheckedAll.value

        assertEquals(expectedTotalPrice, actualTotalPrice)
        assertEquals(expectedTotalCount, actualTotalCount)
        assertEquals(expectedTotalCheckAll, actualTotalCheckAll)
    }

    @Test
    fun 장바구니_상품을_한번에_선택할_수_있다() {
        presenter.checkProductsAll()

        val expectedIndexes = listOf(0, 1, 2, 3, 4)
        val expectedTotalPrice = 50000
        val expectedTotalCount = 5
        val expectedTotalCheckAll = true

        val actualTotalPrice = presenter.cartSystemResult.value?.totalPrice
        val actualTotalCount = presenter.cartSystemResult.value?.totalCount
        val actualTotalCheckAll = presenter.isCheckedAll.value

        expectedIndexes.forEach {
            verify { view.showChangedItem(it) }
        }
        assertEquals(expectedTotalPrice, actualTotalPrice)
        assertEquals(expectedTotalCount, actualTotalCount)
        assertEquals(expectedTotalCheckAll, actualTotalCheckAll)
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
