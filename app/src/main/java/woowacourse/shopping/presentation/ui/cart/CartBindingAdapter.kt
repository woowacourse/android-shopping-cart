package woowacourse.shopping.presentation.ui.cart

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("cartImgUrl")
fun ImageView.setCartImgUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}
