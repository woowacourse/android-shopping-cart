package woowacourse.shopping.data.remote

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.fixture.getFixtureCartItems
import woowacourse.shopping.fixture.getFixtureProducts
import kotlin.concurrent.thread

class MockShoppingWebServerTest {
    private lateinit var server: MockShoppingWebServer
    private lateinit var database: ShoppingDatabase
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    ShoppingDatabase::class.java,
                )
                .allowMainThreadQueries()
                .build()
        server = MockShoppingWebServer(database, 8080)
        client = OkHttpClient()
        database.productDao().addAll(getFixtureProducts(20))
    }

    @After
    fun tearDown() {
        server.shutdown()
        database.close()
    }

    @Test
    fun `유효한_페이지와_아이템_수를_통해_상품_정보를_가져올_수_있다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/products?page=0&page-size=5")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code
            val body = result.body?.string()

            // then
            assertAll(
                { assertThat(code).isEqualTo(200) },
                {
                    assertThat(body).isEqualTo(
                        """
                        [
                            {"product":{"id":1,"imageSource":"image1","name":"사과1","price":1000}},
                            {"product":{"id":2,"imageSource":"image2","name":"사과2","price":2000}},
                            {"product":{"id":3,"imageSource":"image3","name":"사과3","price":3000}},
                            {"product":{"id":4,"imageSource":"image4","name":"사과4","price":4000}},
                            {"product":{"id":5,"imageSource":"image5","name":"사과5","price":5000}}
                        ]
                    """.trimSpacesAndFeedLines(),
                    )
                },
            )
        }.join()
    }

    @Test
    fun `상품_정보_조회_시_페이지_당_상품_수를_입력하지_않으면_클라이언트_에러가_발생한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/products?page=0")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `상품_정보_조회_시_페이지를_입력하지_않으면_클라이언트_에러가_발생한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/products?page-size=0")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `유효한_상품_아이디를_입력하면_특정_상품에_대한_정보를_불러올_수_있다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/product?id=1")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code
            val body = result.body?.string()

            // then
            assertAll(
                { assertThat(code).isEqualTo(200) },
                {
                    assertThat(body).isEqualTo(
                        """
                            {"product":{"id":1,"imageSource":"image1","name":"사과1","price":1000}}
                        """.trimSpacesAndFeedLines(),
                    )
                },
            )
        }.join()
    }

    @Test
    fun `상품_아이디를_입력하지_않으면_특정_상품에_대한_정보를_불러올_수_있다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/product")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `유효한_페이지와_아이템_수를_입력하면_장바구니_페이지_데이터를_불러올_수_있다`() {
        thread {
            // given
            val cartItems = getFixtureCartItems(5)
            cartItems.forEach {
                database.cartDao().addCartItem(it)
            }

            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/cart-items?page=0&page-size=5")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code
            val body = result.body?.string()

            assertAll(
                { assertThat(code).isEqualTo(200) },
                {
                    assertThat(body).isEqualTo(
                        """
                        [
                            {
                                "cartItem":{"id":1,"productId":1,"quantity":1},
                                "product":{"id":1,"imageSource":"image1","name":"사과1","price":1000}
                            },
                            {
                                "cartItem":{"id":2,"productId":2,"quantity":2},
                                "product":{"id":2,"imageSource":"image2","name":"사과2","price":2000}
                            },
                            {
                                "cartItem":{"id":3,"productId":3,"quantity":3},
                                "product":{"id":3,"imageSource":"image3","name":"사과3","price":3000}
                            },
                            {
                                "cartItem":{"id":4,"productId":4,"quantity":4},
                                "product":{"id":4,"imageSource":"image4","name":"사과4","price":4000}
                            },
                            {
                                "cartItem":{"id":5,"productId":5,"quantity":5},
                                "product":{"id":5,"imageSource":"image5","name":"사과5","price":5000}
                            }
                        ]
                        """.trimSpacesAndFeedLines(),
                    )
                },
            )
        }.join()
    }

    @Test
    fun `장바구니_조회_시_페이지_당_상품_수를_입력하지_않으면_클라이언트_에러가_발생한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/cart-items?page=0")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `장바구니_조회_시_페이지를_입력하지_않으면_클라이언트_에러가_발생한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/cart-items?page-size=0")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `유효한_이이템을_바디로_제공하면_장바구니_정보를_삭제한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .delete(convertToJson(CartItem(1, 1, 1)).toRequestBody())
                    .url("http://localhost:8080/cart-item")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(200)
        }.join()
    }

    @Test
    fun `삭제_대상_이이템을_바디로_제공하지_않으면_클라이언트_오류를_발생시킨다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .delete()
                    .url("http://localhost:8080/cart-item")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `유효한_이이템을_바디로_제공하면_장바구니_정보를_추가한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .post(convertToJson(CartItem(6, 7, 21)).toRequestBody())
                    .url("http://localhost:8080/cart-item")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(200)
        }.join()
    }

    @Test
    fun `추가_대상_이이템을_바디로_제공하지_않으면_클라이언트_오류를_발생시킨다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .post("".toRequestBody())
                    .url("http://localhost:8080/cart-item")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `유효한_아이디와_수량을_제공하면_장바구니_아이템의_수량를_수정한다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .patch("{}".toRequestBody())
                    .url("http://localhost:8080/cart-item?id=1&quantity=21")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(200)
        }.join()
    }

    @Test
    fun `수정_대상_이이템을_바디로_제공하지_않으면_클라이언트_오류를_발생시킨다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .patch("".toRequestBody())
                    .url("http://localhost:8080/cart-item")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(400)
        }.join()
    }

    @Test
    fun `장바구니에_담긴_상품의_총_수량을_가져올_수_있다`() {
        thread {
            // given
            database.cartDao().addCartItem(CartItem(1, 1, 1))

            // when
            val request =
                Request.Builder()
                    .url("http://localhost:8080/cart-item/count")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code
            val body = result.body?.string()

            // then
            assertAll(
                { assertThat(code).isEqualTo(200) },
                { assertThat(body).isEqualTo("1") },
            )
        }.join()
    }

    @Test
    fun `유효한_URL을_제공하지_않으면_클라이언트_오류를_발생시킨다`() {
        thread {
            // when
            val request =
                Request.Builder()
                    .patch("".toRequestBody())
                    .url("http://localhost:8080/")
                    .build()
            val result = client.newCall(request).execute()
            val code = result.code

            // then
            assertThat(code).isEqualTo(404)
        }.join()
    }

    private fun String.trimSpacesAndFeedLines(): String {
        return replace("\\s".toRegex(), "")
    }

    private fun <T> convertToJson(data: T): String {
        val gson = Gson()
        return gson.toJson(data)
    }
}
