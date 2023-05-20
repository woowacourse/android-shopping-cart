package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.ProductListContract
import woowacourse.shopping.view.productlist.ProductListPresenter
import woowacourse.shopping.view.productlist.ProductListViewItem

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View

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

        val recentViewedRepository = object : RecentViewedRepository {
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

        presenter = ProductListPresenter(view, productRepository, recentViewedRepository)
    }

    @Test
    fun 최근_본_상품과_20개의_상품들을_띄울_수_있다() {
        val items = slot<List<ProductListViewItem>>()
        every { view.showProducts(capture(items)) } just runs
        presenter.fetchProducts()

        val itemsExpected = listOf<ProductListViewItem>(
            ProductListViewItem.RecentViewedItem(
                listOf(
                    ProductModel(2, "헛개차", "", 10000, 0),
                    ProductModel(1, "현미밥", "", 10000, 0),
                    ProductModel(0, "락토핏", "", 10000, 0)
                )
            )
        ) + listOf<ProductListViewItem>(
            ProductListViewItem.ProductItem(ProductModel(0, "락토핏", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(1, "현미밥", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(2, "헛개차", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(3, "키", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(4, "닭가슴살", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(5, "enffl", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(6, "뽀또", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(7, "둘리", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(8, "안녕", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(9, "9", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(10, "10", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(11, "11", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(12, "12", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(13, "13", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(14, "14", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(15, "15", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(16, "16", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(17, "17", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(18, "18", "", 10000, 0)),
            ProductListViewItem.ProductItem(ProductModel(19, "19", "", 10000, 0)),
        ) + ProductListViewItem.ShowMoreItem()
        assertEquals(itemsExpected.subList(0, 21), items.captured.subList(0, 21))
        assertEquals(itemsExpected[itemsExpected.lastIndex].javaClass, items.captured[items.captured.lastIndex].javaClass)
    }

    @Test
    fun 상품을_추가로_띄울_수_있다() {
        presenter.fetchProducts()

        val mark = slot<Int>()
        every { view.notifyAddProducts(capture(mark), 20) } just runs
        presenter.showMoreProducts()

        val expected = 21
        assertEquals(expected, mark.captured)
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
        Product(42, "42", "", Price(10000))
    )
}
