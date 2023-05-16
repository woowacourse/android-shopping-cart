package woowacourse.shopping.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, imageUrl: String) {
        Glide
            .with(view.context)
            .load(imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setPrice")
    fun setPrice(view: TextView, price: Int) {
        view.text = view.context.getString(R.string.korean_won, PriceFormatter.format(price))
    }
}
