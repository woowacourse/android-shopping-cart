package woowacourse.shopping.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.domain.product.Product

@Composable
fun ProductCardGrid(
    products: List<Product>,
    modifier: Modifier = Modifier,
) {
    var maxProductCount by remember { mutableStateOf(20) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = products.take(maxProductCount),
            key = { item -> item.id },
        ) { item ->
            ProductCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(154.dp),
                imageUrl = item.imageUrl.value,
                productName = item.name.value,
                price = item.price.value,
            )
        }
        if (maxProductCount < products.size) {
            item(
                span = { GridItemSpan(2) },
            ) {
                MoreButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                            .background(
                                color = Color(0xFF555555),
                                shape = RoundedCornerShape(size = 45.dp),
                            ),
                ) {
                    maxProductCount += 20
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    productName: String = "",
    price: Int = 0,
    imageUrl: String = "https://img.freepik.com/free-vector/graident-ai-robot-vectorart_78370-4114.jpg?semt=ais_hybrid&w=740&q=80",
) {
    Box {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = "상품 이미지",
                modifier = modifier,
                contentScale = ContentScale.Crop,
            )
            ProductInfoColumn(
                modifier =
                    Modifier
                        .padding(start = 6.dp, end = 9.dp, top = 8.dp, bottom = 12.dp),
                productName = productName,
                price = price,
            )
        }
    }
}

@Composable
fun ProductInfoColumn(
    modifier: Modifier = Modifier,
    productName: String = "",
    price: Int = 0,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            String.format("%,d", price) + "원",
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text("더보기")
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard()
}

@Preview(showBackground = true)
@Composable
fun ProductInfoColumnPreview() {
    ProductInfoColumn(
        modifier =
            Modifier
                .padding(start = 6.dp, end = 9.dp, top = 8.dp, bottom = 12.dp),
        productName = "아메리카노브랜드라이아이스",
        price = 20000,
    )
}
