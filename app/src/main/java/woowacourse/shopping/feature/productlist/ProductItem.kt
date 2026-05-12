package woowacourse.shopping.feature.productlist

import android.R.attr.name
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.feature.common.ProductQuantitySelector

@Composable
fun ProductItem(
    imageUrl: String,
    name: String,
    price: String,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box {
            PreviewableAsyncImage(
                imageUrl = imageUrl,
                description = name,
                modifier = Modifier.aspectRatio(1f),
            )
            AnimatedContent(
                targetState = quantity == 0,
                transitionSpec = {
                    (fadeIn(tween(200)) + scaleIn(initialScale = 0.8f)) togetherWith
                        (fadeOut(tween(150)) + scaleOut(targetScale = 0.8f))
                },
                label = "quantity-control",
                modifier = Modifier.align(Alignment.BottomEnd),
            ) { isEmpty ->
                if (isEmpty) {
                    ProductInitialAddButton(
                        modifier = Modifier
                            .size(45.dp)
                            .padding(bottom = 4.dp, end = 4.dp)
                            .clickable(onClick = onIncrease),
                    )
                } else {
                    ProductQuantitySelector(
                        quantity = quantity,
                        onIncrease = onIncrease,
                        onDecrease = onDecrease,
                        modifier = Modifier
                            .height(42.dp)
                            .fillMaxWidth()
                            .padding(bottom = 6.dp, start = 4.dp, end = 4.dp),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = name,
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

@Preview
@Composable
private fun PreviewProduct() {
    ProductItem(
        imageUrl = "asd",
        name = "Pet보틀-정사각형 50000ml",
        price = "12,000원",
        onIncrease = {},
        onDecrease = {},
        quantity = 3,
        modifier = Modifier
            .width(160.dp)
            .padding(horizontal = 16.dp),
    )
}

@Composable
@Preview
fun ProductInitialAddButton(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_product_description),
            modifier = Modifier.size(35.dp),
            tint = Color(0xff555555),
        )
    }
}
