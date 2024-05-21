package woowacourse.shopping.presentation.common

import android.content.Context
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import java.text.NumberFormat
import java.util.Locale

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
