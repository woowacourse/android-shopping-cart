package woowacourse.shopping.util.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("goodsImage")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(this.context).load(imageUrl).into(this)
}
