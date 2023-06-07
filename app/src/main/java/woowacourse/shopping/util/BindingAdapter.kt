package woowacourse.shopping.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @androidx.databinding.BindingAdapter("glideSrc")
    fun setGlideImage(imageview: ImageView, image: String) {
        imageview.clipToOutline = true
        imageview.background =
            ContextCompat.getDrawable(imageview.context, R.drawable.rectangle_radius_8dp)
        Glide.with(imageview.context)
            .load(image)
            .fallback(R.drawable.logo_square)
            .error(R.drawable.logo_square)
            .into(imageview)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("isVisible")
    fun isVisible(view: View, visibility: Boolean) {
        view.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }
}
