package woowacourse.shopping.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImageWithGlide")
    fun ImageView.setImageWithGlide(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("bindLiveQuantityText")
    fun TextView.bindLiveQuantityText(quantity: LiveData<Int>?) {
        quantity?.observe(this.findViewTreeLifecycleOwner() ?: return) {
            this.text = it.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("bindLiveTotalPriceText")
    fun TextView.bindLiveTotalPriceText(totalPrice: LiveData<Int>?) {
        totalPrice?.observe(this.findViewTreeLifecycleOwner() ?: return) { price ->
            this.text = context.getString(R.string.price, price)
        }
    }
}
