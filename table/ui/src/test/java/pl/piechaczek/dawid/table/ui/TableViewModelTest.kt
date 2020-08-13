package pl.piechaczek.dawid.table.ui

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pl.piechaczek.dawid.core.data.model.RateModel
import pl.piechaczek.dawid.core.data.model.TableModel
import pl.piechaczek.dawid.table.data.usecase.TableUseCase
import pl.piechaczek.dawid.table.ui.model.Rate
import pl.piechaczek.dawid.table.ui.table.DefaultTableViewModel
import pl.piechaczek.dawid.table.ui.table.TableViewAction
import pl.piechaczek.dawid.table.ui.table.TableViewEffect
import pl.piechaczek.dawid.table.ui.table.TableViewState

class TableViewModelTest {

    @Mock
    private lateinit var tableUseCase: TableUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Effect should show table with rates when list is not empty`() {
        val tableId = 'A'
        tableUseCase.stub {
            on { getTable(tableId) } doReturn Single.just(listOf(exampleTableModel))
        }
        val viewModel = createSUT()
        val testObserver = viewModel.observeEffects().test()

        viewModel.onAction(TableViewAction.GetInfoForTable(tableId))
            .test()
            .assertComplete()

        testObserver
            .assertValues(
                TableViewEffect.ShowProgress,
                showTableEffect,
                TableViewEffect.HideProgress
            )
    }

    @Test
    fun `Effect should show toast with info when list is empty`() {
        val tableId = 'A'
        tableUseCase.stub {
            on { getTable(tableId) } doReturn Single.just(listOf())
        }
        val viewModel = createSUT()
        val testObserver = viewModel.observeEffects().test()

        viewModel.onAction(TableViewAction.GetInfoForTable(tableId))
            .test()
            .assertComplete()

        testObserver
            .assertValues(
                TableViewEffect.ShowProgress,
                TableViewEffect.ShowToast("Tabela jest pusta"),
                TableViewEffect.HideProgress
            )
    }

    @Test
    fun `State should change segment when tab is changed`() {
        val newItemIndex = 3
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()

        viewModel.onAction(TableViewAction.SegmentChanged(3))
            .test()
            .assertComplete()

        testObserver
            .assertValues(
                TableViewState(selectedSegment = newItemIndex)
            )
    }

    private fun createSUT(
        effect: PublishSubject<TableViewEffect> = PublishSubject.create(),
        state: BehaviorSubject<TableViewState> = BehaviorSubject.create()
    ) = DefaultTableViewModel(
        effect,
        state,
        tableUseCase
    )

    private val exampleTableModel = TableModel(
        table = 'A',
        no = "20/NBP/160/2020",
        effectiveDate = "20-08-2020",
        rates = listOf(
            RateModel("euro", "EUR", 0.987f),
            RateModel("polski złoty", "PLN", 0.142f)
        )
    )

    private val showTableEffect = TableViewEffect.ShowTable(
        tableType = exampleTableModel.table,
        rates = listOf(
            Rate(
                date = exampleTableModel.effectiveDate,
                currencyName = exampleTableModel.rates[0].currency,
                code = exampleTableModel.rates[0].code,
                rate = exampleTableModel.rates[0].mid
            ),
            Rate(
                date = exampleTableModel.effectiveDate,
                currencyName = exampleTableModel.rates[1].currency,
                code = exampleTableModel.rates[1].code,
                rate = exampleTableModel.rates[1].mid
            )
        )
    )
}