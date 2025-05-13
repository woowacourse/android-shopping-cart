package woowacourse.shopping.view.products

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.Product

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        adapter =
            ProductsAdapter(
                listOf(
                    Product(
                        imageUrl = "",
                        title = "사탕",
                        price = 10000,
                    ),
                    Product(
                        imageUrl = "",
                        title = "사탕",
                        price = 10000,
                    ),
                    Product(
                        imageUrl = "",
                        title = "사탕",
                        price = 10000,
                    ),
                ),
            )
        binding.rvProducts.adapter = adapter

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
