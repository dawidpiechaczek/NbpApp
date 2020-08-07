package pl.piechaczek.dawid.table.ui.model

import pl.piechaczek.dawid.core.data.model.RateModel

data class Rate(
    val date: String,
    val currencyName: String,
    val code: String,
    val rate: Float
)


fun RateModel.map(effectiveDate: String) = Rate(
    date = effectiveDate,
    currencyName = currency,
    code = code,
    rate = mid
)