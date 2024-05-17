package woowacourse.shopping.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object CommonBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(
        imageView: ImageView,
        url: String?,
    ) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.img_default_product)
            .fallback(R.drawable.img_default_product)
            .error(R.drawable.img_error_product)
            .into(imageView)
    }

    @BindingAdapter("visibility")
    @JvmStatic
    fun setVisibility(
        view: View,
        isVisible: Boolean?,
    ) {
        view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
    }
}
