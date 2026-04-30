package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.state.ProductUiModel

@Composable
fun ProductList(
    products: List<ProductUiModel>,
    onProductClick: (String) -> Unit,
    onLoading: () -> Unit,
    modifier: Modifier = Modifier,
    isEnd: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        items(
            key = { it.id },
            items = products,
        ) {
            SingleProductItem(
                imageUrl = it.imageUrl,
                title = it.title,
                price = it.price,
                modifier = Modifier.clickable(
                    onClick = {
                        onProductClick(it.id)
                    },
                ),
            )
        }
        if (isEnd.not())
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onLoading()
                        },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "상품 더보기",
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = Color(0xFF555555),
                    )
                }
            }
    }
}
