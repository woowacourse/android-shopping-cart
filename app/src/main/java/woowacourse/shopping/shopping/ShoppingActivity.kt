package woowacourse.shopping.shopping

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.contract.ShoppingContract
import woowacourse.shopping.shopping.contract.presenter.ShoppingPresenter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    override lateinit var presenter: ShoppingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        presenter = ShoppingPresenter(this, ProductMockRepository)
        presenter.initProductsRecyclerView()
    }

    override fun initProductsRecyclerView(data: List<ProductUIModel>) {
        binding.productRecyclerview.adapter = ProductsAdapter(data) { presenter.onItemClick(it) }
        val layoutManager = GridLayoutManager(this@ShoppingActivity, 2)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val spanCount = 2

        binding.productRecyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) { // 1부터 시작
                val position = parent.getChildAdapterPosition(view)
                val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)

                if (spanSize == spanCount) {
                    // 첫 번째 아이템인 경우
                    outRect.left = (parent.width - view.layoutParams.width) / 2
                    outRect.right = (parent.width - view.layoutParams.width) / 2
                } else {
                    // 나머지 아이템인 경우
                    outRect.left = spacing
                    outRect.right = spacing
                }

                outRect.top = spacing
            }
        })
        binding.productRecyclerview.layoutManager = layoutManager
    }

    override fun onItemClick(data: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, data))
    }
}
