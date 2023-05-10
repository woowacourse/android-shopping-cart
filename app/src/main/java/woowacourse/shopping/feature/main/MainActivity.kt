package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var mainProductAdapter: MainProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = MainPresenter(
            this,
            ProductMockRepository()
        )
        mainProductAdapter = MainProductAdapter(listOf())
        binding.productRv.adapter = mainProductAdapter
        presenter.loadProducts()
    }

    override fun showCartScreen() {
        TODO("Not yet implemented")
    }

    override fun showProductDetailScreen(position: Int) {
        val product = mainProductAdapter.items[position].product
        startActivity(DetailActivity.getIntent(this, product))
    }

    override fun addProducts(products: List<MainProductItemModel>) {
        mainProductAdapter.addItems(products)
    }
}
