package woowacourse.shopping.presentation.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("app:imageUrl")
fun loadImage(
    view: ImageView,
    url: String?,
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("app:price")
fun setPrice(
    view: TextView,
    price: Long?,
) {
    price?.let {
        view.text = view.context.getString(R.string.price_format, it)
    } ?: run {
        view.text = ""
    }
}

@BindingAdapter("app:selectedBasedOn")
fun setSelectedBasedOn(
    button: AppCompatButton,
    isSelected: Boolean?,
) {
    button.isSelected = isSelected == true
}

@BindingAdapter("app:visibilityTv")
fun setVisibility(
    view: TextView,
    visibility: Boolean?,
) {
    visibility?.let {
        view.visibility = if (it) RecyclerView.VISIBLE else RecyclerView.GONE
    }
}
