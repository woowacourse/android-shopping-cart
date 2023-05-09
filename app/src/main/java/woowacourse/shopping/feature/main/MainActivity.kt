package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.list.item.ProductItem
import woowacourse.shopping.feature.product.detail.ProductDetailActivity

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productRv.adapter = ProductListAdapter(
            ProductItem.getDummy()
        ) { ProductDetailActivity.startActivity(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
