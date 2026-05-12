package woowacourse.shopping.feature.productlist

import android.R.attr.onClick
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.feature.common.state.ProductUiModel

@Composable
fun ProductList(
    products: List<ProductUiModel>,
    onProductClick: (ProductUiModel) -> Unit,
    onLoading: () -> Unit,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnd: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            key = { it.id },
            items = products,
        ) {
            ProductItem(
                imageUrl = it.imageUrl,
                name = it.name,
                price = it.formattedPrice(it.quantity),
                quantity = it.quantity,
                onIncrease = { onIncrease(it.id) },
                onDecrease = { onDecrease(it.id) },
                modifier = Modifier.clickable(
                    onClick = {
                        onProductClick(it)
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
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = stringResource(R.string.load_more_description),
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

@Preview
@Composable
private fun ProductListPreview() {
    ProductList(
        products = emptyList(),
        onProductClick = { },
        onLoading = { },
        onIncrease = { },
        onDecrease = { },
        isEnd = false,
    )
}
