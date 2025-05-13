package woowacourse.shopping

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.databinding.ActivityProductsBinding

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        applyWindowInsets()

        val adapter = ProductAdapter(mockProducts())
        binding.recyclerViewProducts.adapter = adapter
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mockProducts(): List<Product> =
        listOf(
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
            Product(name = "아메리카노", imageUrl = R.drawable.iced_americano, price = 10000),
        )
}
