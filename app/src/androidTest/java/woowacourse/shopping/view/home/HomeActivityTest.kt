package woowacourse.shopping.view.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication.Companion.recentProductDatabase
import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepositoryImpl
import woowacourse.shopping.helper.testProduct0

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUp() {
        recentProductRepository = RecentProductRepositoryImpl(recentProductDatabase)
        recentProductRepository.deleteAll()
    }

    @Test
    fun `화면에_상품_목록이_나타난다`() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.rv_product_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `화면에_장바구니_버튼이_보인다`() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.btn_shopping_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `초기_화면에_최근_본_상품_목록이_비어있으면_텅_화면이_보인다`() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.rv_recent_products_empty)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_recent_products)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `화면에_최근_본_상품_목록_보인다`() {
        recentProductRepository.save(testProduct0)

        ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.rv_recent_products_empty)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rv_recent_products)).check(matches(isDisplayed()))
    }
}
