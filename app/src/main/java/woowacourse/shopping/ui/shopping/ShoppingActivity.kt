package woowacourse.shopping.ui.shopping

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View
import woowacourse.shopping.ui.shopping.recyclerview.adapter.loadmore.LoadMoreAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.product.ProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct.RecentProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct.RecentProductWrapperAdapter
import woowacourse.shopping.util.factory.createShoppingPresenter
import woowacourse.shopping.util.isolatedViewTypeConfig

class ShoppingActivity : AppCompatActivity(), View, OnMenuItemClickListener {
    private lateinit var binding: ActivityShoppingBinding

    private val shoppingDatabase by lazy { ShoppingDatabase(this) }
    override val presenter: Presenter by lazy { createShoppingPresenter(this, shoppingDatabase) }

    private val recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProductDetail)
    private val recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductAdapter)
    private val productAdapter = ProductAdapter(presenter::inquiryProductDetail)
    private val loadMoreAdapter = LoadMoreAdapter(presenter::fetchProducts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initView()
    }

    private fun initView() {
        binding.presenter = presenter
        binding.tbShopping.setOnMenuItemClickListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.adapter = ConcatAdapter(
            isolatedViewTypeConfig, recentProductWrapperAdapter, productAdapter, loadMoreAdapter
        )
        presenter.fetchAll()
    }

    override fun updateProducts(products: List<UiProduct>) {
        productAdapter.submitList(products)
    }

    override fun updateRecentProducts(recentProducts: List<UiRecentProduct>) {
        recentProductWrapperAdapter.submitList(recentProducts)
    }

    override fun showProductDetail(product: UiProduct) {
        startActivity(ProductDetailActivity.getIntent(this, product))
    }

    override fun navigateToBasketScreen() {
        startActivity(BasketActivity.getIntent(this))
    }

    override fun showLoadMoreButton() {
        loadMoreAdapter.showButton()
    }

    override fun hideLoadMoreButton() {
        loadMoreAdapter.hideButton()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.basket -> presenter.openBasket()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        shoppingDatabase.close()
    }

    companion object {
        fun insertDummies(context: Context, size: Int) {
            (0 until size).forEach { id ->
                ProductDaoImpl(ShoppingDatabase(context)).add(
                    DataProduct(
                        id,
                        "name $id",
                        DataPrice(1000),
                        "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg"
                    )
                )
            }
        }
    }
}
