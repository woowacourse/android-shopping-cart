package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl
import woowacourse.shopping.data.datasource.product.LocalProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.PRODUCT
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.RECENT_PRODUCTS
import woowacourse.shopping.ui.shopping.recentproduct.RecentProductAdapter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    override lateinit var presenter: ShoppingPresenter
    private lateinit var binding: ActivityShoppingBinding

    private lateinit var shoppingAdapter: ShoppingAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initPresenter()
        initAdapter()
    }

    private fun initPresenter() {
        val shoppingDatabase = ShoppingDatabase(this)
        presenter = ShoppingPresenter(
            this,
            ProductRepository(
                LocalProductDataSource(ProductDaoImpl(shoppingDatabase))
            ),
            RecentProductRepository(
                LocalRecentProductDataSource(RecentProductDaoImpl(shoppingDatabase))
            )
        )
    }

    override fun updateProducts(products: List<UiProduct>) {
        shoppingAdapter.submitList(products)
    }

    override fun updateRecentProducts(recentProducts: List<UiProduct>) {
        shoppingAdapter.updateRecentProduct(recentProducts)
    }

    private fun initAdapter() {
        recentProductAdapter = RecentProductAdapter { }
        shoppingAdapter = ShoppingAdapter(recentProductAdapter) { product -> } // start Activity
        binding.rvShopping.adapter = shoppingAdapter
        val gridLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (ShoppingViewHolderType.getName(shoppingAdapter.getItemViewType(position))) {
                        RECENT_PRODUCTS -> 2
                        PRODUCT -> 1
                    }
            }
        }
        binding.rvShopping.layoutManager = gridLayoutManager
        presenter.fetchRecentProducts()
        presenter.fetchProducts()
    }
}
