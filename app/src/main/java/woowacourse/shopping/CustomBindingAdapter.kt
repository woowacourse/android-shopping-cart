package woowacourse.shopping

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DecimalFormat

object CustomBindingAdapter {

    @BindingAdapter("imgResId")
    @JvmStatic
    fun setImageResource(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("price", "format")
    @JvmStatic
    fun setPrice(view: TextView, price: Int, format: String) {
        val text = format.format(DecimalFormat("#,###").format(price))
        view.text = text
    }
}
