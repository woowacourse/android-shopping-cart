package woowacourse.shopping.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageWithGlide")
    fun ImageView.setImageWithGlide(imageUrl: String?) {
        if (imageUrl == null) {
            this.setImageResource(R.drawable.ic_close)
            return
        }

        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun View.setVisible(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
