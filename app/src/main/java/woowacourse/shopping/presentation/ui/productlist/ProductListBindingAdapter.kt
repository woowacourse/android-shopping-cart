package woowacourse.shopping.presentation.ui.productlist

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("bindUrlToImage")
fun ImageView.urlToImage(imageUrl: String?) {
    imageUrl?.let { url ->
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("bindPriceToCurrency")
fun TextView.priceToCurrency(price: Int?) {
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
