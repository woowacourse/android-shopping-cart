package woowacourse.shopping.ui.shopping

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.component.ProductAsyncImage
import woowacourse.shopping.ui.component.QuantitySelector
import woowacourse.shopping.ui.theme.Gray50
import woowacourse.shopping.ui.util.formattedPrice

@Composable
fun ProductCard(
    imageUrl: String,
    productName: String,
    price: Int,
    quantity: Int,
    onClick: () -> Unit,
    onQuantityChange: (Int) -> Unit,
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
            contentAlignment = Alignment.BottomCenter,
        ) {
            ProductAsyncImage(
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            if (quantity == 0) {
                Box(
                    modifier =
                        Modifier
                            .padding(bottom = 8.dp, end = 8.dp)
                            .size(48.dp)
                            .background(Color.White, CircleShape)
                            .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "추가",
                        tint = Gray50,
                        modifier =
                            Modifier
                                .size(32.dp)
                                .clickable { onQuantityChange(1) },
                    )
                }
            } else {
                QuantitySelector(
                    quantity = quantity,
                    onQuantityChange = onQuantityChange,
                    modifier = Modifier.padding(bottom = 8.dp),
                    contentColor = Gray50,
                )
            }
        }

        Text(
            text = productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
        )
        Text(
            text = formattedPrice(price),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 20.sp,
            color = Gray50,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        imageUrl = "",
        productName = "커피",
        price = 1000,
        quantity = 0,
        onClick = {},
        onQuantityChange = {},
    )
}
