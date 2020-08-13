package pl.piechaczek.dawid.details.ui.model

import pl.piechaczek.dawid.core.data.model.DetailsRateModel

data class Rate(
    val serialCode: String,
    val date: String,
    val rate: Float
)

fun DetailsRateModel.map() =
    Rate(
        serialCode = no,
        date = effectiveDate,
        rate = mid
    )