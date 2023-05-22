package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.ProductMockWebRepository
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel
import woowacourse.shopping.view.productlist.ProductListContract
import woowacourse.shopping.view.productlist.ProductListPresenter

class ProductListPresenterTest {
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var view: ProductListContract.View

    private lateinit var productRepository: ProductMockWebRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var recentViewedRepository: RecentViewedRepository

    private val fakeRecentViewedData = listOf(0, 1, 2, 3)

    private val fakeProducts = listOf(
        Product(
            0,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(10000),
        ),
        Product(
            1,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(10000),
        ),
        Product(
            2,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(10000),
        ),
    )

    val fakeProduct = Product(
        0,
        "락토핏",
        "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
        Price(10000),
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        recentViewedRepository = mockk(relaxed = true)

        presenter =
            ProductListPresenter(
                view,
                productRepository,
                recentViewedRepository,
                cartRepository,
            )
    }

    @Test
    fun 최근_본_상품과_20개의_상품들을_띄울_수_있다() {
        every { recentViewedRepository.findAll() } returns fakeRecentViewedData
        every { productRepository.get() } returns fakeProducts
        every { productRepository.find(any()) } returns fakeProduct
        val viewedProductsActual = slot<List<ProductModel>>()
        val productsActual = slot<List<ProductModel>>()
        every {
            view.showProducts(
                capture(viewedProductsActual),
                capture(productsActual),
            )
        } just runs
        presenter.fetchProducts()

        val viewedProductExpected =
            listOf(0, 1, 2).map { id -> products.find { it.id == id }?.toUiModel() }
                .sortedByDescending { it?.id }
        val productsExpected = products.subList(0, 20).map { it.toUiModel() }
        assertEquals(viewedProductExpected, viewedProductsActual.captured)
        assertEquals(productsExpected, productsActual.captured)
    }

    @Test
    fun 상품을_추가로_띄울_수_있다() {
        val mark = slot<Int>()
        every { view.notifyAddProducts(capture(mark), 20) } just runs
        presenter.showMoreProducts()

        val expected = 21
        assertEquals(expected, mark.captured)
    }

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
        Product(
            9,
            "9",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            10,
            "10",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            11,
            "11",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            12,
            "12",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            13,
            "13",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            14,
            "14",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            15,
            "15",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            16,
            "16",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            17,
            "17",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            18,
            "18",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            19,
            "19",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            20,
            "20",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            21,
            "21",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            22,
            "22",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            23,
            "23",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            24,
            "24",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            25,
            "25",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            26,
            "26",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            27,
            "27",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            28,
            "28",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            29,
            "29",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            30,
            "30",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            31,
            "31",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            32,
            "32",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            33,
            "33",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            34,
            "34",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            35,
            "35",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            36,
            "36",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            37,
            "37",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            38,
            "38",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            39,
            "39",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            40,
            "40",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            41,
            "41",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            42,
            "42",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),

    )
}
