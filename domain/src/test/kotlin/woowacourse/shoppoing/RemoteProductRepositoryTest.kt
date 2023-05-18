package woowacourse.shoppoing

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.junit.Test
import woowacourse.shopping.model.Product
import woowacourse.shopping.repositoryImpl.RemoteProductRepository

class RemoteProductRepositoryTest {
    @Test
    fun `상품 목록을 가져온다`() {
        // given
        val mockWebServer = startMockWebServer()
        val remoteProductRepository = RemoteProductRepository(
            mockWebServer.url("").toString()
        )

        // when
        val products = remoteProductRepository.getAll()

        // then
        assertThat(products).hasSize(2)
        assertThat(products[0].id).isEqualTo(1)
        assertThat(products[0].name).isEqualTo("치킨")
        assertThat(products[0].price).isEqualTo(10000)
        assertThat(products[0].imageUrl).isEqualTo("http://example.com/chicken.jpg")
        assertThat(products[1].id).isEqualTo(2)
        assertThat(products[1].name).isEqualTo("피자")
        assertThat(products[1].price).isEqualTo(20000)
        assertThat(products[1].imageUrl).isEqualTo("http://example.com/pizza.jpg")
    }

    @Test
    fun `다음 상품들을 가져온다_1`() {
        // given
        val mockWebServer = startMockWebServer()
        val remoteProductRepository = RemoteProductRepository(
            mockWebServer.url("").toString()
        )

        // when
        val products = remoteProductRepository.getNext(10)

        // then
        assertThat(products).hasSize(10)
        for (i in 0..9) {
            assertThat(products[i].id).isEqualTo(i + 1)
            assertThat(products[i].name).isEqualTo("치킨")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo("http://example.com/chicken.jpg")
        }
    }

    @Test
    fun `다음 상품들을 가져온다_2`() {
        // given
        val mockWebServer = startMockWebServer()
        val remoteProductRepository = RemoteProductRepository(
            mockWebServer.url("").toString()
        )

        // when
        remoteProductRepository.getNext(10)
        val products = remoteProductRepository.getNext(10)

        // then
        println(products)
        assertThat(products).hasSize(10)
        for (i in 0..9) {
            assertThat(products[i].id).isEqualTo(i + 11)
            assertThat(products[i].name).isEqualTo("치킨")
            assertThat(products[i].price).isEqualTo(10000)
            assertThat(products[i].imageUrl).isEqualTo("http://example.com/chicken.jpg")
        }
    }

    @Test
    fun `ID로 상품을 가져온다`() {
        // given
        val mockWebServer = startMockWebServer()
        val remoteProductRepository = RemoteProductRepository(
            mockWebServer.url("").toString()
        )

        // when
        val product = remoteProductRepository.findById(1)

        // then
        assertThat(product.id).isEqualTo(1)
        assertThat(product.name).isEqualTo("치킨")
        assertThat(product.price).isEqualTo(10000)
        assertThat(product.imageUrl).isEqualTo("http://example.com/chicken.jpg")
    }

    @Test
    fun `상품을 등록할 수 있다`() {
        // given
        val mockWebServer = startMockWebServer()
        val remoteProductRepository = RemoteProductRepository(
            mockWebServer.url("").toString()
        )
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
