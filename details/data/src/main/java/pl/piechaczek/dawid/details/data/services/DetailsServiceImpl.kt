package pl.piechaczek.dawid.details.data.services

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.model.DetailsTableModel
import pl.piechaczek.dawid.core.data.service.NbpService
import retrofit2.Response
import javax.inject.Inject

class DetailsServiceImpl @Inject constructor(
    private val service: NbpService
) : DetailsService {

    override fun getRatesForDates(
        tableId: Char,
        code: String,
        startDate: String,
        endDate: String
    ): Single<Response<DetailsTableModel>> =
        service.getRatesForDates(tableId, code, startDate, endDate)
}