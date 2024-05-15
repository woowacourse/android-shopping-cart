package woowacourse.shopping.presentation.ui.productlist

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("urlToImage")
fun ImageView.bindUrlToImage(imageUrl: String?) {
    imageUrl?.let { url ->
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("priceToCurrency")
fun TextView.bindPriceToCurrency(price: Int?) {
    price?.let { priceValue ->
        this.text = priceValue.currency(context)
    }
}

fun Int.currency(context: Context): String {
    return when (Locale.getDefault().country) {
        Locale.KOREA.country -> context.getString(R.string.price_format_kor, this)
        else -> NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
    }
}

@BindingAdapter("loadMoreBtnVisible")
fun TextView.binLoadMoreBtnVisible(last: Boolean) {
    if (last) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}
