package woowacourse.shopping.ui.productlist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductDummy
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.product.Product

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyWindowInsets()

        val adapter = productListAdapter()
        binding.productsRecyclerView.adapter = adapter
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun productListAdapter(): ProductListAdapter {
        return ProductListAdapter(
            items = ProductDummy.products,
            object : ProductClickListener {
                override fun onClick(product: Product) {
                    // 상세 페이지로 이동
                }
            },
        )
    }
}
