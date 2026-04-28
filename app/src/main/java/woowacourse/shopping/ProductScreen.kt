@file:Suppress("FunctionName")

package woowacourse.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "${product.title} 상품 이미지",
            modifier =
                Modifier
                    .size(154.dp)
                    .background(Color.Gray),
        )
        Text(
            text = product.title,
            modifier = Modifier,
        )
        Text(
            text = product.price.toString(),
            modifier = Modifier,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    ProductItem(
        product =
            Product(
                id = 1,
                title = "동원 스위트콘",
                price = 99_800,
                imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
            ),
    )
}
