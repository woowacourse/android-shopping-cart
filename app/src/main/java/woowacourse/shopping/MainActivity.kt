package woowacourse.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        adapter =
            ProductAdapter(onClickProductItem = {
                // TODO 상품 상세 화면 이동
            })
        binding.rvMainProduct.adapter = adapter
        adapter.updateProducts(dummyProducts)
    }
}
