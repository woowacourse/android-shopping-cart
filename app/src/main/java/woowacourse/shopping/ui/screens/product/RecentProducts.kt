package woowacourse.shopping.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.domain.RecentProduct

@Composable
fun RecentProducts(
    items: List<RecentProduct>,
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("최근 본 상품", fontSize = 16.sp)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items,
                key = { it.productId },
            ) {
                RecentProductCard(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    onClickItem = { onClickItem(it.productId) },
                )
            }
        }
    }
}

@Composable
private fun RecentProductCard(
    name: String,
    imageUrl: String,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .width(98.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$name 이미지 입니다용",
            modifier = Modifier
                .size(98.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 4.dp),
            color = Color(0xff333333),
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.W700,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentProductsPreview() {
    RecentProducts(
        items = listOf(
            RecentProduct(
                productId = "1",
                name = "고양이",
                imageUrl = "",
                viewedAt = 1,
            ),
            RecentProduct(
                productId = "2",
                name = "고양이",
                imageUrl = "",
                viewedAt = 2,
            ),
        ),
        onClickItem = { },
    )
}

@Preview(showBackground = true)
@Composable
private fun RecentProduct() {
    RecentProductCard(
        name = "asdf",
        imageUrl = "",
        onClickItem = {},
    )
}
