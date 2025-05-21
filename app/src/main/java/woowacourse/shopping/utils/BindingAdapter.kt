package woowacourse.shopping.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageWithGlide")
    fun ImageView.setImageWithGlide(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }
}
