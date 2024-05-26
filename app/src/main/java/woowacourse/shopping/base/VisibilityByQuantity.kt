package woowacourse.shopping.base

import android.view.View
import androidx.databinding.BindingAdapter

object VisibilityByQuantity {
    @JvmStatic
    @BindingAdapter("app:visibilityByQuantity")
    fun setVisibilityByQuantity(view: View, quantity: Int) {
        view.visibility = if (quantity > 0) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibilityByPlusButton")
    fun setVisibilityByPlusButton(view: View, quantity: Int) {
        view.visibility = if (quantity > 0) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("app:visibilityByBoolean")
    fun setVisibilityByBoolean(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
