package woowacourse.shopping.feature

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter

@BindingAdapter("imgUrl")
fun loadImageFromUrl(
    imageView: ImageView,
    url: String,
) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("items")
fun RecyclerView.bindItems(items: List<Goods>?) {
    if (adapter is GoodsAdapter && items != null) {
        (adapter as GoodsAdapter).setItems(items)
    }
}

@BindingAdapter("visible")
fun Button.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
