package app.siakad.siakadtkadmin.presentation.views.alert

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import app.siakad.siakadtkadmin.R

class AlertDialogFragment(private val title: String, private val msg: String): DialogFragment() {
    private lateinit var alertDialog: AlertDialog.Builder
    private var alertListener: AlertListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alertDialog
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ya") { _, _ ->
                alertListener?.alertAction()
            }
            .setNegativeButton("Tidak") { dialogInterface, _ -> dialogInterface.dismiss() }

        return alertDialog?.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
        alertDialog = AlertDialog.Builder(context)
        alertListener = context as AlertListener
    }

    override fun onDetach() {
        super.onDetach()
        if (alertListener != null) {
            alertListener = null
        }
    }
}