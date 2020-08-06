package pl.piechaczek.dawid.nbpapp

import io.reactivex.Single
import pl.piechaczek.dawid.core.ui.extension.toBodyOrError
import javax.inject.Inject

class TableUseCase @Inject constructor(
    private val nbpService: NbpService
) {

    fun getTable(tableId: Char): Single<List<TableModel>> {
        return nbpService
            .getTable(tableId)
            .retry(1)
            .map { it.toBodyOrError() }
    }
}