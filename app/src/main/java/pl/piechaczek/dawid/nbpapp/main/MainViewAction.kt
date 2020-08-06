package pl.piechaczek.dawid.nbpapp.main

sealed class MainViewAction {
    data class SegmentChanged(val newItemIndex: Int) : MainViewAction()
    object ShowToast : MainViewAction()
}
