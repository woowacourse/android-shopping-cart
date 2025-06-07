@file:Suppress("ktlint")

package woowacourse.shopping.view.page

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.domain.Product
import woowacourse.shopping.util.allInventoryProducts

class InventoryRepositoryImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var productDatabase: ProductDatabase
    private lateinit var repository: InventoryRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        productDatabase =
            Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        repository = InventoryRepositoryImpl(productDatabase.productDao())
        allInventoryProducts.forEach { product ->
            repository.insert(product)
        }
    }

    @Test
    fun 페이지의_크기만큼_항목이_들어있다() {
        val expected = listOf(
            Product(
                0,
                "[병천아우내] 모듬순대",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            ),
            Product(
                1,
                "[빙그래] 요맘때 파인트 710mL 3종 (택1)",
                5000,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/73061aab-a2e2-443a-b0f9-f19b7110045e.jpg",
            ),
            Product(
                2,
                "[애슐리] 크런치즈엣지 포테이토피자 495g",
                10900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/23efcafe-0765-478f-afe9-f9af7bb9b7df.jpg",
            ),
        )
        repository.getPage(3, 0) { page ->
            assertThat(page.items).isEqualTo(expected)
        }
    }

    @Test
    fun 첫_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        repository.getPage(3, 0) { page ->
            assertThat(page.hasPrevious).isFalse()
            assertThat(page.hasNext).isTrue()
        }
    }

    @Test
    fun 첫_페이지의_인덱스는_0이다() {
        repository.getPage(5, 0) { page ->
            val actual = page.pageIndex
            val expected = 0
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun 중간에_있는_페이지는_이전_페이지와_다음_페이지가_있다() {
        repository.getPage(5, 1) { page ->
            assertThat(page.hasPrevious).isTrue()
            assertThat(page.hasNext).isTrue()
        }
    }

    @Test
    fun 마지막_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        repository.getPage(5, 5) { page ->
            assertThat(page.hasPrevious).isTrue()
            assertThat(page.hasNext).isFalse()
        }
    }
}
