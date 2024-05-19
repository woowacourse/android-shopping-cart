package woowacourse.shopping.presentation.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.shopping.ShoppingAdapter

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

@BindingAdapter("app:product")
fun setItems(
    recyclerView: RecyclerView,
    products: List<Product>?,
) {
    products?.let {
        (recyclerView.adapter as? ShoppingAdapter)?.loadData(it)
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

@BindingAdapter("app:setLoadMoreBtnVisibility")
fun setLoadMoreBtnVisibility(
    view: TextView,
    isVisible: Boolean?,
) {
    if (isVisible == true) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:cartViewVisibility")
fun setViewVisibility(
    view: View,
    uiState: UIState<List<CartItem>>?,
) {
    when (uiState) {
        is UIState.Empty -> {
            if (view is TextView) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }

        else -> {
            if (view is TextView) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }
}
