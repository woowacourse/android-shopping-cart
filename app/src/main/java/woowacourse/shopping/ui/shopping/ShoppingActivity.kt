package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.ui.model.UiProduct

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    override lateinit var presenter: ShoppingPresenter

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initPresenter()
        initAdapter()
    }

    private fun initPresenter() {
        presenter = ShoppingPresenter(
            this,
            RecentProductRepository(
                LocalRecentProductDataSource(RecentProductDaoImpl(ShoppingDatabase(this)))
            )
        )
    }

    override fun updateRecentProducts(recentProducts: List<UiProduct>) {
        shoppingAdapter.updateRecentProduct(recentProducts)
    }

    private fun initAdapter() {
        shoppingAdapter = ShoppingAdapter { product -> } // start Activity
        binding.rvShopping.adapter = shoppingAdapter
        val gridLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when (position) {
                    0 -> 2
                    else -> 1
                }
            }
        }
        binding.rvShopping.layoutManager = gridLayoutManager
        presenter.fetchRecentProducts()
    }
}
