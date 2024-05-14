package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.feature.main.adapter.ProductAdapter
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.viewmodel.ProductsViewModel
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: ProductAdapter
    private val productsViewModel by viewModels<ProductsViewModel>()
    private val productRepository: ProductRepository by lazy { ProductRepositoryImpl }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        adapter =
            ProductAdapter(onClickProductItem = { productId ->
                ProductDetailActivity.newIntent(this, productId)
                    .also { startActivity(it) }
            })
        binding.rvMainProduct.adapter = adapter
        productsViewModel.products.observe(this) {
            adapter.updateProducts(it)
        }
        productsViewModel.update(productRepository) // TODO: 네이밍 고민. load vs update vs ...

        binding.toolbarMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_cart -> {
                    // TODO: 장바구니 화면 이동
                }
            }
            false
        }
    }
}
