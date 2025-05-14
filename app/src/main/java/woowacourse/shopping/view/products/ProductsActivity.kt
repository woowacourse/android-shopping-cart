package woowacourse.shopping.view.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.products.Products
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        adapter =
            ProductsAdapter(Products().value) { product ->
                val intent =
                    Intent(this, ProductDetailActivity::class.java).apply {
                        putExtra("product", product)
                    }
                startActivity(intent)
            }

        binding.cartImageBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
        binding.rvProducts.adapter = adapter
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
    }
}
