package woowacourse.shopping.presentation.ui.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.assertj.core.api.Assertions

class RecyclerViewItemCountAssertion(private val expectedItemCount: Int) : ViewAssertion {
    override fun check(
        view: View?,
        noViewFoundException: NoMatchingViewException?,
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        Assertions.assertThat(adapter?.itemCount).isEqualTo(expectedItemCount)
    }
}
