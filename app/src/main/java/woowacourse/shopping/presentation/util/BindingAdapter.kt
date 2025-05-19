package woowacourse.shopping.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import java.text.DecimalFormat

@BindingAdapter("goodsPrice")
fun TextView.setPrice(price: Int) {
    text = context.getString(R.string.text_price, DecimalFormat("#,###").format(price))
}

@BindingAdapter("goodsImage")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(this.context).load(imageUrl).into(this)
}
