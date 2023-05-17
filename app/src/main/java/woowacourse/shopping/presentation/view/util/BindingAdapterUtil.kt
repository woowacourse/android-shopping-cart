package woowacourse.shopping.presentation.view.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:img")
fun ImageView.setImage(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}
