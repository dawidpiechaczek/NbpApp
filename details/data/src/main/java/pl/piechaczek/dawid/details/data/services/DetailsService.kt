package pl.piechaczek.dawid.details.data.services

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.model.DetailsTableModel
import retrofit2.Response

interface DetailsService {

    fun getRatesForDates(
        tableId: Char,
        code: String,
        startDate: String,
        endDate: String
    ): Single<Response<DetailsTableModel>>
}