package woowacourse.shopping.shopping

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.data.ProductFakeRepository
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.contract.ShoppingContract
import woowacourse.shopping.shopping.contract.presenter.ShoppingPresenter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        setSupportActionBar(binding.toolbar)
        presenter = ShoppingPresenter(this, ProductFakeRepository, RecentProductDatabase(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> navigateToCart()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        presenter.setUpProducts()
    }

    override fun setProducts(data: List<ProductsItemType>) {
        println((data[0] as RecentProductsItem).product.map { it.name })
        binding.productRecyclerview.adapter = ProductsAdapter(
            data,
            presenter::navigateToItemDetail,
            presenter::fetchMoreProducts
        )
        val layoutManager = GridLayoutManager(this@ShoppingActivity, 2)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val spanCount = 2

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (binding.productRecyclerview.adapter?.getItemViewType(position)) {
                    ProductsItemType.TYPE_FOOTER -> spanCount
                    ProductsItemType.TYPE_ITEM -> 1
                    else -> spanCount
                }
            }
        }

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
//                    outRect.left = (parent.width - view.layoutParams.width) / 2
//                    outRect.right = (parent.width - view.layoutParams.width) / 2
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

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, product))
    }

    override fun addProducts(data: List<ProductsItemType>) {
        binding.productRecyclerview.adapter?.let {
            if (it is ProductsAdapter) {
                it.updateData(data)
            }
        }
    }

    private fun navigateToCart() {
        startActivity(CartActivity.from(this))
    }
}
