package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productdetail.ProductDetailContract
import woowacourse.shopping.view.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter

    private val products = listOf(
        Product(
            0,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            1,
            "현미밥",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            2,
            "헛개차",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            3,
            "키",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            4,
            "닭가슴살",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            5,
            "enffl",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            6,
            "뽀또",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            7,
            "둘리",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            8,
            "안녕",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
    )

    private val cartProducts = mutableListOf(
        CartProduct(
            0,
            1,
            true,
        ),
        CartProduct(
            0,
            1,
            true,
        ),
        CartProduct(
            0,
            1,
            true,
        ),
        CartProduct(
            0,
            1,
            true,
        ),
    )

    private val productRepository = object : ProductRepository {
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

        override fun find(id: Int): Int? {
            return id
        }

        override fun findMostRecent(): Int {
            return 2
        }
    }

    private val cartRepository = object : CartRepository {
        val myCart =
            mutableListOf(
                CartProduct(1, 1, true),
                CartProduct(2, 2, true),
                CartProduct(3, 3, true),
            )
        var count = 1

        override fun findAll(): List<CartProduct> {
            return myCart
        }

        override fun add(id: Int, count: Int, check: Boolean) {
            myCart.add(CartProduct(id, count, check))
        }

        override fun remove(id: Int) {
            myCart.remove(CartProduct(id, id, true))
        }

        override fun findRange(mark: Int, rangeSize: Int): List<CartProduct> {
            return myCart
        }

        override fun isExistByMark(mark: Int): Boolean {
            return myCart.size >= mark
        }

        override fun plusCount(id: Int) {
            count++
        }

        override fun subCount(id: Int) {
            count--
        }

        override fun findCheckedItem(): List<CartProduct> {
            return myCart
        }

        override fun updateCheckState(id: Int, checked: Boolean) {
        }
    }

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = ProductDetailPresenter(
            view,
            cartRepository,
            recentViewedRepository,
            productRepository,
        )
    }

    @Test
    fun `장바구니 담기 버튼을 클릭하면 장바구니에 상품이 담긴다`() {
        every { view.startCartActivity() } answers { nothing }

        val product = ProductModel(
            10,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            10000,
        )
        presenter.putInCart(product)
        val expectedSize = 5
        val actualSize = cartRepository.findAll().size

        assertEquals(expectedSize, actualSize)
        verify { view.startCartActivity() }
    }

    @Test
    fun `상품 상세 페이지로 들어가면 최근 본 상품에 해당 상품이 등록된다`() {
        val id = 1
        presenter.updateRecentViewedProducts()

        val expectedSize = 4
        val actualSize = recentViewedRepository.findAll().size

        assertEquals(expectedSize, actualSize)
    }
}
