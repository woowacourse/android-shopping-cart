package woowacourse.shopping.feature

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.vertical.MoreButtonAdapter

@BindingAdapter("imgUrl")
fun loadImageFromUrl(
    imageView: ImageView,
    url: String?,
) {
    url?.let {
        val placeholderDrawable =
            ContextCompat.getDrawable(imageView.context, R.drawable.image_placeholder)
        Glide
            .with(imageView.context)
            .load(url)
            .placeholder(placeholderDrawable)
            .error(placeholderDrawable)
            .into(imageView)
    }
}

@BindingAdapter("cartItems")
fun RecyclerView.bindCartItems(cartItems: List<CartItem>?) {
    when (val adapter = this.adapter) {
        is GoodsAdapter -> {
            if (cartItems != null) adapter.setItems(cartItems)
        }

        is CartAdapter -> {
            if (cartItems != null) adapter.setItems(cartItems)
        }

        is ConcatAdapter -> {
            adapter.adapters.forEach { childAdapter ->
                if (childAdapter is GoodsAdapter && cartItems != null) {
                    childAdapter.setItems(cartItems)
                }

                if (childAdapter is CartAdapter && cartItems != null) {
                    childAdapter.setItems(cartItems)
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
