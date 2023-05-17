package woowacourse.shopping.util.extension

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

@BindingAdapter("formatMoneyWon")
fun TextView.formatPriceWon(money: Int) {
    text = "${DecimalFormat("#,###").format(money)}원"
}
