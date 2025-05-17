package woowacourse.shopping.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageUrl")
fun ImageView.setImage(image: String?) {
    image ?: return
    Glide
        .with(this.context)
        .load(image)
        .placeholder(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .into(this)
}
