package pl.piechaczek.dawid.core.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import pl.piechaczek.dawid.core.ui.databinding.DialogCalendarBinding
import timber.log.Timber

const val dateFormat = "yyyy-MM-dd"

open class CalendarDialog : DialogFragment() {
    internal lateinit var callback: CalendarDialogCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        try {
            callback = targetFragment as CalendarDialogCallback
        } catch (exception: ClassCastException) {
            Timber.e("Target fragment ${targetFragment!!::class.java.name} doesn't implement required dialog callback")
            throw exception
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = LayoutInflater.from(requireContext())
        val binding = DialogCalendarBinding.inflate(inflater)
        val onDateChangeListener: DatePicker.OnDateChangedListener = OnDateChangeListenerImpl()

        arguments?.let { args ->
            val date = args.getString(ARG_DATA)
            if (date != null) {
                val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat))
                binding.calendar.init(
                    localDate.year,
                    localDate.monthValue,
                    localDate.dayOfMonth,
                    onDateChangeListener
                )
            } else {
                val today = LocalDate.now()
                binding.calendar.init(
                    today.year,
                    today.monthValue - 1,
                    today.dayOfMonth,
                    onDateChangeListener
                )
            }
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        arguments?.let {
            callback.onDateSelected(it.getInt(ARG_REQUEST_ID), null)
        }
    }

    fun <T> setSafeTargetFragment(target: T) where T : Fragment, T : CalendarDialogCallback {
        super.setTargetFragment(target, 0)
    }

    inner class OnDateChangeListenerImpl : DatePicker.OnDateChangedListener {
        override fun onDateChanged(
            view: DatePicker?,
            year: Int,
            monthOfYear: Int,
            dayOfMonth: Int
        ) {
            callback.onDateSelected(
                arguments?.getInt(ARG_REQUEST_ID)!!,
                LocalDate.of(year, monthOfYear, dayOfMonth)
            )
            dismiss()
        }
    }


    companion object {
        internal const val ARG_DATA = "ARG_DATA"
        internal const val ARG_REQUEST_ID = "ARG_REQUEST_ID"

        @JvmStatic
        fun newInstance(date: LocalDate?, requestId: Int = 0): CalendarDialog {
            return CalendarDialog().apply {
                arguments = Bundle().apply {
                    date?.let {
                        putString(ARG_DATA, it.format(DateTimeFormatter.ofPattern(dateFormat)))
                    }
                    putInt(ARG_REQUEST_ID, requestId)
                }
            }
        }
    }
}

interface CalendarDialogCallback {
    fun onDateSelected(requestId: Int, date: LocalDate?)
}