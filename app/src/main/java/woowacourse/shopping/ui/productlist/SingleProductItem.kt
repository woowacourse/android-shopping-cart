package woowacourse.shopping.ui.productlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun SingleProductItem(
    imageUrl: String,
    title: String,
    price: String,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PreviewableAsyncImage(
                imageUrl = imageUrl,
                description = title,
                modifier = Modifier.aspectRatio(1f),
            )

            if (quantity == 0) {
                AddCartButton(
                    onIncrement = onIncrement,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                )
            } else {
                QuantityCounter(
                    quantity = quantity,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(42.dp)
                        .padding(vertical = 8.dp, horizontal = 14.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = title,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = price,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            color = Color(0xff555555),
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun AddCartButton(
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onIncrement,
        shape = CircleShape,
        color = Color.White,
        modifier = modifier.size(40.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.add_icon),
            contentDescription = stringResource(R.string.cart_add_description),
        )
    }
}

@Composable
private fun QuantityCounter(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color.White,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onDecrement),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "-",
                    fontSize = 22.sp,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$quantity",
                    fontSize = 22.sp,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onIncrement),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+",
                    fontSize = 22.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSingleProduct() {
    SingleProductItem(
        imageUrl = "asd",
        title = "Pet보틀-정사각형 50000ml",
        price = "12,000원",
        modifier = Modifier
            .width(160.dp)
            .padding(horizontal = 16.dp),
        quantity = 0,
        onIncrement = {},
        onDecrement = {},
    )
}

@Preview
@Composable
private fun PreviewSingleProductWithQuantity() {
    SingleProductItem(
        imageUrl = "asd",
        title = "Pet보틀-정사각형 50000ml",
        price = "12,000원",
        modifier = Modifier
            .width(160.dp)
            .padding(horizontal = 16.dp),
        quantity = 1,
        onIncrement = {},
        onDecrement = {},
    )
}
