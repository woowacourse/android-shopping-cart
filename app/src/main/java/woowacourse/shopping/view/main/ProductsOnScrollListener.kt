package woowacourse.shopping.view.main

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ActivityMainBinding

class ProductsOnScrollListener(
    private val binding: ActivityMainBinding,
    private val viewModel: ProductsViewModel,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        binding.btnLoadMoreProducts.visibility =
            if (
                lastVisibleItemPosition == layoutManager.itemCount - 1 &&
                viewModel.totalSize > (recyclerView.adapter?.itemCount ?: 0)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
