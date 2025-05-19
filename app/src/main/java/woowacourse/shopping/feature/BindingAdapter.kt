package woowacourse.shopping.feature

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.MoreButtonAdapter

@BindingAdapter("imgUrl")
fun loadImageFromUrl(
    imageView: ImageView,
    url: String?,
) {
    url?.let {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}

@BindingAdapter("items")
fun RecyclerView.bindItems(items: List<Goods>?) {
    when (val adapter = this.adapter) {
        is GoodsAdapter -> {
            if (items != null) adapter.setItems(items)
        }
        is CartAdapter -> {
            if (items != null) adapter.setItems(items)
        }
        is ConcatAdapter -> {
            adapter.adapters.forEach { childAdapter ->
                if (childAdapter is GoodsAdapter && items != null) {
                    childAdapter.setItems(items)
                }
                if (childAdapter is CartAdapter && items != null) {
                    childAdapter.setItems(items)
                }
            }
        }
    }
}

@BindingAdapter("moreButtonVisible")
fun RecyclerView.setMoreButtonVisible(visible: Boolean) {
    val adapter = this.adapter
    if (adapter is ConcatAdapter) {
        adapter.adapters.forEach { childAdapter ->
            if (childAdapter is MoreButtonAdapter) {
                childAdapter.setVisibility(visible)
            }
        }
    } else if (adapter is MoreButtonAdapter) {
        adapter.setVisibility(visible)
    }
}

@BindingAdapter("layout_visible")
fun LinearLayout.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
