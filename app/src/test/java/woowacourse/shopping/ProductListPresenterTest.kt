package woowacourse.shopping

import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.ProductListContract
import woowacourse.shopping.view.productlist.ProductListPresenter
import woowacourse.shopping.view.productlist.ProductListViewItem

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        val productRepository = object : ProductRepository {
            private val mProducts = products
            override fun findAll(): List<Product> {
                return mProducts
            }

            override fun find(id: Int): Product {
                return Product(0, "락토핏", "", Price(10000))
            }

            override fun findRange(mark: Int, rangeSize: Int): List<Product> {
                return listOf(
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
                )
            }

            override fun isExistByMark(mark: Int): Boolean {
                if (mark < 0) return false
                return true
            }
        }

        val recentViewedRepository = object : RecentViewedRepository {
            override fun findAll(): List<Int> {
                return listOf(0)
            }

            override fun add(id: Int) {
            }

            override fun remove(id: Int) {
            }
        }

        cartRepository = object : CartRepository {
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

        presenter =
            ProductListPresenter(view, productRepository, recentViewedRepository, cartRepository)
    }

    @Test
    fun 최근_본_상품과_20개의_상품들을_띄울_수_있다() {
        val items = slot<List<ProductListViewItem>>()
        every { view.showProducts(capture(items)) } just runs
        presenter.fetchProducts()

        val itemsExpected = listOf<ProductListViewItem>(
            ProductListViewItem.RecentViewedItem(
                listOf(
                    ProductModel(0, "락토핏", "", 10000, 1),
                ),
            ),
        ) + listOf<ProductListViewItem>(
            ProductListViewItem.ProductItem(ProductModel(0, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(1, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(2, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(3, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(4, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(5, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(6, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(7, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(8, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(9, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(10, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(11, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(12, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(13, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(14, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(15, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(16, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(17, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(18, "락토핏", "", 10000, 1)),
            ProductListViewItem.ProductItem(ProductModel(19, "락토핏", "", 10000, 1)),
        ) + ProductListViewItem.ShowMoreItem()
        assertEquals(itemsExpected.subList(0, 21), items.captured.subList(0, 21))
        assertEquals(
            itemsExpected[itemsExpected.lastIndex].javaClass,
            items.captured[items.captured.lastIndex].javaClass,
        )
    }

    @Test
    fun 상품을_추가로_띄울_수_있다() {
        presenter.fetchProducts()

        val mark = slot<Int>()
        every { view.notifyAddProducts(capture(mark), 20) } just runs
        presenter.loadMoreProducts()

        val expected = 21
        assertEquals(expected, mark.captured)
    }

    @Test
    fun 상품_상세_정보를_띄울_수_있다() {
        val productSlot = slot<ProductModel>()
        val viewedProductSlot = slot<ProductModel>()
        every {
            view.onClickProductDetail(
                capture(productSlot),
                capture(viewedProductSlot),
            )
        } just runs

        presenter.fetchProducts()
        presenter.showProductDetail(ProductModel(0, "락토핏", "", 10000, 0))

        val expectedProduct = ProductModel(0, "락토핏", "", 10000, 0)
        val expectedViewedProduct = ProductModel(0, "락토핏", "", 10000, 1)

        assertEquals(expectedProduct, productSlot.captured)
        assertEquals(expectedViewedProduct, viewedProductSlot.captured)
    }

    @Test
    fun 장바구니에_상품을_추가할_수_있다() {
        presenter.fetchProducts()
        presenter.addToCartProducts(0, 1)

        val actual = cartRepository.findAll().size

        assertEquals(9, actual)
    }

    @Test
    fun 장바구니_상품_개수를_1이상으로_지정하면_업데이트할_수_있다() {
        presenter.fetchProducts()
        presenter.updateCartProductCount(0, 2)

        val actual = cartRepository.find(0)?.count

        assertEquals(1, actual)
        verify { view.notifyDataChanged(1) }
    }

    @Test
    fun 장바구니_상품_개수를_0으로_지정하면_장바구니에서_삭제한다() {
        presenter.fetchProducts()
        presenter.updateCartProductCount(0, 0)

        verify { view.notifyDataChanged(1) }
    }

    @Test
    fun 장바구니_상품_개수를_띄울_수_있다() {
        val expected = slot<Int>()

        every { view.showCartCount(capture(expected)) } just runs

        presenter.fetchProducts()
        presenter.fetchCartCount()

        assertEquals(expected.captured, 8)
    }

    @Test
    fun 업데이트된_상품_개수를_띄울_수_있다() {
        presenter.fetchProducts()
        presenter.updateCartProductCount(0, 5)
        presenter.updateCartProductCount(1, 10)
        presenter.fetchProductCounts()

        verify { view.notifyDataChanged(1) }
        verify { view.notifyDataChanged(2) }
    }

    @Test
    fun 특정_상품_개수를_업데이트할_수_있다() {
        presenter.fetchProducts()
        presenter.fetchProductCount(0)

        verify { view.notifyDataChanged(1) }
    }

    private val products = listOf(
        Product(0, "락토핏", "", Price(10000)),
        Product(1, "현미밥", "", Price(10000)),
        Product(2, "헛개차", "", Price(10000)),
        Product(3, "키", "", Price(10000)),
        Product(4, "닭가슴살", "", Price(10000)),
        Product(5, "enffl", "", Price(10000)),
        Product(6, "뽀또", "", Price(10000)),
        Product(7, "둘리", "", Price(10000)),
        Product(8, "안녕", "", Price(10000)),
        Product(9, "9", "", Price(10000)),
        Product(10, "10", "", Price(10000)),
        Product(11, "11", "", Price(10000)),
        Product(12, "12", "", Price(10000)),
        Product(13, "13", "", Price(10000)),
        Product(14, "14", "", Price(10000)),
        Product(15, "15", "", Price(10000)),
        Product(16, "16", "", Price(10000)),
        Product(17, "17", "", Price(10000)),
        Product(18, "18", "", Price(10000)),
        Product(19, "19", "", Price(10000)),
        Product(20, "20", "", Price(10000)),
        Product(21, "21", "", Price(10000)),
        Product(22, "22", "", Price(10000)),
        Product(23, "23", "", Price(10000)),
        Product(24, "24", "", Price(10000)),
        Product(25, "25", "", Price(10000)),
        Product(26, "26", "", Price(10000)),
        Product(27, "27", "", Price(10000)),
        Product(28, "28", "", Price(10000)),
        Product(29, "29", "", Price(10000)),
        Product(30, "30", "", Price(10000)),
        Product(31, "31", "", Price(10000)),
        Product(32, "32", "", Price(10000)),
        Product(33, "33", "", Price(10000)),
        Product(34, "34", "", Price(10000)),
        Product(35, "35", "", Price(10000)),
        Product(36, "36", "", Price(10000)),
        Product(37, "37", "", Price(10000)),
        Product(38, "38", "", Price(10000)),
        Product(39, "39", "", Price(10000)),
        Product(40, "40", "", Price(10000)),
        Product(41, "41", "", Price(10000)),
        Product(42, "42", "", Price(10000)),
    )
}
