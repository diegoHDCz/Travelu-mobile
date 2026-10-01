package br.com.travelu.feature.travel.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.travelu.R
import br.com.travelu.feature.travel.domain.model.RatingTier

@Composable
fun RatingTier.displayName(): String = when (this) {
    RatingTier.EXCEPTIONAL -> stringResource(R.string.rating_exceptional)
    RatingTier.EXCELLENT -> stringResource(R.string.rating_excellent)
    RatingTier.VERY_GOOD -> stringResource(R.string.rating_very_good)
    RatingTier.GOOD -> stringResource(R.string.rating_good)
    RatingTier.AVERAGE -> stringResource(R.string.rating_average)
    RatingTier.BELOW_AVERAGE -> stringResource(R.string.rating_below_average)
}
