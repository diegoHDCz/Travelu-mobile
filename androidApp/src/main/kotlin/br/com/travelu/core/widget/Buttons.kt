package br.com.travelu.core.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun TraveluCircleImageButton(
    modifier: Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    Image(
        imageVector = imageVector, contentDescription = contentDescription,
        modifier = modifier
            .padding(16.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            .clickable {
                onClick()
            }
            .padding(8.dp)
    )
}

@Composable
fun TraveluCircleImageButton(
    modifier: Modifier,
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String
) {
    Image(
        painter = painter, contentDescription = contentDescription,
        modifier = modifier
            .padding(16.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            .clickable {
                onClick()
            }
            .padding(8.dp)
    )
}

