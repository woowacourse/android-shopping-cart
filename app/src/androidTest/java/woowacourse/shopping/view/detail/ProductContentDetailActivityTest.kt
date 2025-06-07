@file:Suppress("ktlint")

package woowacourse.shopping.view.detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.util.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.util.isDisplayed
import woowacourse.shopping.util.matchText
import woowacourse.shopping.util.performClick
import woowacourse.shopping.util.inventoryItem

class ProductContentDetailActivityTest {
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>

    @get:Rule
    val productDetailActivityScenarioRule = ActivityScenarioRule(ProductDetailActivity::class.java)

    @Before
    fun setUp() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val intent = ProductDetailActivity.newIntent(fakeContext, inventoryItem.id)
        scenario = ActivityScenario.launch(intent)
    }

    @SuppressLint("CheckResult")
    @Test
    fun 상품의_이름_이미지와_가격이_표시된다() {
        onView(withId(R.id.iv_product_detail_image)).isDisplayed()
        onView(withId(R.id.tv_product_name)).matchText("[병천아우내] 모듬순대")
        onView(withId(R.id.tv_product_price)).matchText("11,900원")
    }
}
