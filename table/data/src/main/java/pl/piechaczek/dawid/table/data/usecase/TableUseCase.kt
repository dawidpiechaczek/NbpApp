package pl.piechaczek.dawid.table.data.usecase

import io.reactivex.Single
import pl.piechaczek.dawid.core.data.extension.toBodyOrError
import pl.piechaczek.dawid.core.data.model.TableModel
import pl.piechaczek.dawid.table.data.service.TableService
import javax.inject.Inject

class TableUseCase @Inject constructor(
    private val tableService: TableService
) {

    fun getTable(tableId: Char): Single<List<TableModel>> {
        return tableService
            .getTable(tableId)
            .retry(1)
            .map { it.toBodyOrError() }
    }
}