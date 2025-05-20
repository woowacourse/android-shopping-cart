package woowacourse.shopping.view.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("app:imageUrl")
fun setImageUrl(
    imageView: ImageView,
    imageUrl: String,
) {
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("app:visibility")
fun changeVisibility(
    view: View,
    visible: Boolean,
) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:buttonColor")
fun changeButtonColor(
    button: ImageButton,
    hasMultiplePages: Boolean,
) {
    val colorResId = if (hasMultiplePages) R.color.aqua_green else R.color.gray_6
    val color = ContextCompat.getColor(button.context, colorResId)
    button.setBackgroundColor(color)
}
