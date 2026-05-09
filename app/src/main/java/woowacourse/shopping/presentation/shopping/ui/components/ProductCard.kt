package woowacourse.shopping.presentation.shopping.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.presentation.common.QuantityCounter
import woowacourse.shopping.presentation.common.model.ProductUiModel
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import woowacourse.shopping.ui.theme.Gray50
import woowacourse.shopping.util.formattedPrice

@Composable
fun ProductCard(
    product: ProductUiModel,
    quantity: Int,
    onClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            if (quantity == 0) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_product_to_cart),
                    tint = Gray50,
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(14.dp)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onIncrease() },
                )
            } else {
                QuantityCounter(
                    quantity = quantity,
                    onIncrease = onIncrease,
                    onDecrease = onDecrease,
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(vertical = 8.dp, horizontal = 14.dp),
                )
            }
        }
        Text(
            text = product.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
        )
        Text(
            text = formattedPrice(product.price),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            color = Gray50,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InCartProductCardPreview() {
    ProductCard(
        product =
            ProductUiModel(
                id = 1L,
                name = "아메리카노",
                price = 6000,
                imageUrl = "",
            ),
        quantity = 1,
        onClick = {},
        onIncrease = {},
        onDecrease = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun NoCartProductCardPreview() {
    AndroidshoppingTheme {
        ProductCard(
            product =
                ProductUiModel(
                    id = 1L,
                    name = "아메리카노",
                    price = 6000,
                    imageUrl = "",
                ),
            quantity = 0,
            onClick = {},
            onIncrease = {},
            onDecrease = {},
        )
    }
}
