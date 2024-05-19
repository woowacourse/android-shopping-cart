package woowacourse.shopping.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.hamcrest.Matcher


/**
 * 리사이클러뷰의 expectedCount만큼 아이템이 있는지 확인하는 ViewAssertion
 */
class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        adapter.shouldNotBeNull()
        adapter.itemCount shouldBe expectedCount
    }
}

fun withItemCount(expectedCount: Int): ViewAssertion {
    return RecyclerViewItemCountAssertion(expectedCount)
}

/**
 * 리사이클러뷰의 특정 위치에 있는 아이템을 클릭하는 ViewAction
 *
 * reference: https://gist.github.com/quentin7b/9c5669fd940865cf2e89
 */
fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click on specific button";
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id)
            v.performClick()
        }
    }
}