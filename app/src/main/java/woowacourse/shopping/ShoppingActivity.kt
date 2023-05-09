package woowacourse.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val example = ProductUiModel(
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002487]_20210426091745467.jpg",
            name = "아메리카노",
            price = 5000
        )

        val exampleList = List(10) { example }

        val recyclerView = findViewById<RecyclerView>(R.id.product_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ShoppingRecyclerAdapter(exampleList, ::navigateToProductDetailView)
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        val intent = ProductDetailActivity.getIntent(this, product)
        startActivity(intent)
    }
}
