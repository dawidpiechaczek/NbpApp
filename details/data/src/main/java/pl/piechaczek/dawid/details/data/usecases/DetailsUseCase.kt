package pl.piechaczek.dawid.details.data.usecases

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.extension.toBodyOrError
import pl.piechaczek.dawid.core.data.model.DetailsTableModel
import pl.piechaczek.dawid.details.data.services.DetailsService
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    private val detailsService: DetailsService
) {

    fun getRatesForDates(
        tableId: Char,
        code: String,
        startDate: String,
        endDate: String
    ): Single<DetailsTableModel> {
        return detailsService
            .getRatesForDates(tableId, code, startDate, endDate)
            .retry(1)
            .map { it.toBodyOrError() }
    }
}
