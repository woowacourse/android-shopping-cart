package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:image")
fun ImageView.setImage(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}
