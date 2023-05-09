package woowacourse.shopping.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.mapper.toItem
import woowacourse.shopping.feature.mapper.toUi
import woowacourse.shopping.feature.model.ProductState
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
            items = ProductState.getDummy().map(ProductState::toItem),
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
