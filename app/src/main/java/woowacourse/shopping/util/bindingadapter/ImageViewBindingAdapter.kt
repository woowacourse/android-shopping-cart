package woowacourse.shopping.util.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.util.extension.showImage

@BindingAdapter("bind:imageUrl")
fun ImageView.setImageUrl(imageUrl: String) {
    showImage(imageUrl)
}
