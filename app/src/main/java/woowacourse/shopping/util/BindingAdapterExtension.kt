package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageGlide")
fun setImageResource(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .error(R.drawable.default_image)
        .centerCrop()
        .into(view)
}
