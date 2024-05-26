package woowacourse.shopping.ui.productDetail

import android.view.ViewGroup
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["latestProductId", "currentProductId"])
fun ViewGroup.latestProductVisibility(
    latestProductId: Long,
    currentProductId: Long,
) {
    visibility =
        when (latestProductId) {
            -1L -> ViewGroup.GONE
            currentProductId -> ViewGroup.GONE
            else -> ViewGroup.VISIBLE
        }
}
