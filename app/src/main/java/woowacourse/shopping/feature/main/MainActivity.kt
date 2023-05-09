package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var mainProductAdapter: MainProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.productRv
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
        TODO("Not yet implemented")
    }

    override fun addProducts(products: List<MainProductItemModel>) {
        mainProductAdapter.addItems(products)
    }
}
