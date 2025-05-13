package woowacourse.shopping.product.catalog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.detail.DetailActivity.Companion.newIntent

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        applyWindowInsets()

        val mockProducts = MockProducts().mockProducts

        val adapter =
            ProductAdapter(mockProducts.map { it.toUiModel() }) { product ->
                val intent = newIntent(this, product)
                startActivity(intent)
            }
        binding.recyclerViewProducts.adapter = adapter
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
