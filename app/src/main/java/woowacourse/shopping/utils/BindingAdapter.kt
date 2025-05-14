package woowacourse.shopping.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageWithGlide")
    fun ImageView.setImageWithGlide(imageUrl: String) {
        Glide.with(this.context)
            .load(imageUrl)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("setFormattedPrice")
    fun TextView.formatted(price: Int) {
        this.text = this.context.getString(R.string.price, PriceFormatter.format(price))
    }
}
