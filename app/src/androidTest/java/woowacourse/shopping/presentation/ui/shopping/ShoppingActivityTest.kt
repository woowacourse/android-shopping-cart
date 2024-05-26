package woowacourse.shopping.presentation.ui.shopping

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.InMemoryCartRepository
import woowacourse.shopping.data.repository.InMemoryRecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.presentation.ui.testProductWithQuantity0
import woowacourse.shopping.presentation.ui.testRecentlyViewedProduct0

@RunWith(AndroidJUnit4::class)
class ShoppingActivityTest {
    private lateinit var context: Context
    private lateinit var recentlyViewedProductsRepository: RecentlyViewedProductsRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        recentlyViewedProductsRepository =
            InMemoryRecentlyViewedProductsRepository((context as ShoppingApplication).appDatabase)
        cartRepository = InMemoryCartRepository((context as ShoppingApplication).appDatabase)
    }

    @After
    fun tearDown() {
        // 데이터베이스 초기화
        (context as ShoppingApplication).appDatabase.clearAllTables()
    }

    @Test
    fun `화면에_상품_목록이_나타난다`() {
        ActivityScenario.launch(ShoppingActivity::class.java)
        onView(withId(R.id.rv_product_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `화면에_장바구니_버튼이_보인다`() {
        ActivityScenario.launch(ShoppingActivity::class.java)
        onView(withId(R.id.iv_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니_버튼을_클릭하면_장바구니_화면으로_이동한다`() {
        ActivityScenario.launch(ShoppingActivity::class.java)
        onView(withId(R.id.iv_cart)).perform(click())
        onView(withId(R.id.tv_empty_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니에_상품을_추가하면_장바구니_개수가_보인다`() {
        // Given
        cartRepository.insert(testProductWithQuantity0)

        // When
        val scenario = ActivityScenario.launch(ShoppingActivity::class.java)

        // Then
        onView(withId(R.id.tv_total_count)).check(matches(isDisplayed()))
    }

    @Test
    fun `최근_본_상품이_없는_경우에_문구가_보인다`() {
        ActivityScenario.launch(ShoppingActivity::class.java)
        onView(withId(R.id.tv_empty_recently_viewed)).check(matches(isDisplayed()))
    }

    @Test
    fun `최근_본_상품이_있는_경우에_목록이_보인다`() {
        // Given
        recentlyViewedProductsRepository.insertRecentlyViewedProduct(testRecentlyViewedProduct0)

        // When
        val scenario = ActivityScenario.launch(ShoppingActivity::class.java)

        // Then
        onView(withId(R.id.rv_recently_viewed)).check(matches(isDisplayed()))
    }
}
