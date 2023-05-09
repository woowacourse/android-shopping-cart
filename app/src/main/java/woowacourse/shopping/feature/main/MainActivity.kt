package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.Product
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.mapper.toItem
import woowacourse.shopping.feature.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity

class MainActivity : AppCompatActivity(), MainContract.View {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    lateinit var adapter: ProductListAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this)

        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        adapter = ProductListAdapter(
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                }
            }
        )
        binding.productRv.adapter = adapter
    }

    override fun setProducts(products: List<Product>) {
        adapter.setItems(products.map { it.toUi().toItem() })
    }
}
