package woowacourse.shopping.presentation.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide
        .with(this.context)
        .load(url)
        .fallback(R.drawable.ic_delete)
        .error(R.drawable.ic_delete)
        .into(this)
}
