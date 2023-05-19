package woowacourse.shoppoing

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.Product
import woowacourse.shopping.repositoryImpl.MockWeb
import woowacourse.shopping.repositoryImpl.RemoteProductRepository

class RemoteProductRepositoryTest {
    private lateinit var mockWebServer: MockWeb
    private lateinit var remoteProductRepository: RemoteProductRepository

    @Before
    fun setUp() {
        // given
        mockWebServer = MockWeb()
        remoteProductRepository = RemoteProductRepository(
            mockWebServer.url
        )
    }

    @Test
    fun `상품 목록을 가져온다`() {
        // when
        val products = remoteProductRepository.getAll()

        // then
        assertThat(products).hasSize(100)
        for (i in 0..99) {
            assertThat(products[i].id).isEqualTo(i)
            assertThat(products[i].name).isEqualTo("치킨$i")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo(
                "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            )
        }
    }

    @Test
    fun `다음 상품들을 가져온다_1`() {
        // when
        val products = remoteProductRepository.getNext(10)

        // then
        assertThat(products).hasSize(10)
        for (i in 0..9) {
            assertThat(products[i].id).isEqualTo(i)
            assertThat(products[i].name).isEqualTo("치킨$i")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo(
                "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            )
        }
    }

    @Test
    fun `다음 상품들을 가져온다_2`() {
        // when
        remoteProductRepository.getNext(10)
        val products = remoteProductRepository.getNext(10)

        // then
        println(products)
        assertThat(products).hasSize(10)
        for (i in 0..9) {
            assertThat(products[i].id).isEqualTo(i + 10)
            assertThat(products[i].name).isEqualTo("치킨${i + 10}")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo(
                "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            )
        }
    }

    @Test
    fun `다음 상품드을 가져온다_3`() {
        // when
        val products = remoteProductRepository.getNext(20)

        // then
        println(products)
        assertThat(products).hasSize(20)
        for (i in 0..9) {
            assertThat(products[i].id).isEqualTo(i)
            assertThat(products[i].name).isEqualTo("치킨$i")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo(
                "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
            )
        }
    }

    @Test
    fun `ID로 상품을 가져온다`() {
        // when
        val product = remoteProductRepository.findById(1)

        // then
        println(product)
        assertThat(product.id).isEqualTo(1)
        assertThat(product.name).isEqualTo("치킨1")
        assertThat(product.price).isEqualTo(10000)
        assertThat(product.imageUrl).isEqualTo(
            "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
        )
    }

    @Test
    fun `상품을 등록할 수 있다`() {
        val product = Product(
            id = 999,
            name = "치킨",
            price = 10000,
            imageUrl = "http://example.com/chicken.jpg"
        )

        // when
        remoteProductRepository.insert(product)

        // then
        val request = mockWebServer.takeRequest()
        val json = JSONArray(request.body.readUtf8()).getJSONObject(0)
        json.let {
            println(it)
            assertThat(it.getInt("id")).isEqualTo(999)
            assertThat(it.getString("name")).isEqualTo("치킨")
            assertThat(it.getInt("price")).isEqualTo(10000)
            assertThat(it.getString("imageUrl")).isEqualTo("http://example.com/chicken.jpg")
        }

        assertThat(request.path).isEqualTo("/products")
    }
}
