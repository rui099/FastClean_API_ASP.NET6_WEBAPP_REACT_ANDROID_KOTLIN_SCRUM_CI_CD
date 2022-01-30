package com.example.fastclean.Fragments.Payments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.braintreepayments.cardform.view.CardForm

import com.example.fastclean.R
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Fragments.Perfil.WorkerProfileFragment
import com.example.fastclean.Repositories.SubRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.SubViewModel
import com.example.fastclean.ViewModels.Perfil.SubViewModelFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val retrofitService = RetrofitService.getInstance()
    private lateinit var viewModel: SubViewModel

    lateinit var cardForm: CardForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_payment, container, false)

        viewModel =
            ViewModelProvider(this, SubViewModelFactory(SubRepository(retrofitService))).get(
                SubViewModel::class.java
            )

        cardForm = v.findViewById(R.id.card_form)
        val buy: Button = v.findViewById(R.id.btnBuy)

        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .mobileNumberExplanation("SMS is required on this number")
            .setup(this.activity);

        cardForm.cvvEditText.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD


        buy.setOnClickListener(this)
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btnBuy) {
            if (cardForm.isValid()) {
                val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
                alertBuilder.setTitle("Confirm before purchase")
                alertBuilder.setMessage(
                    """
                            Card number: ${cardForm.cardNumber}
                            Card expiry date: ${cardForm.expirationDateEditText.text.toString()}
                            Card CVV: ${cardForm.cvv}
                            Postal code: ${cardForm.postalCode}
                            Phone number: ${cardForm.mobileNumber}
                            """.trimIndent()
                )
                alertBuilder.setPositiveButton("Confirm",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        Toast.makeText(
                            this.context,
                            "Obrigado por subscrever",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.putSub(UserSession.getId())
                        val fragment = WorkerProfileFragment()
                        val fragmentManager = activity?.supportFragmentManager
                        val fragmentTransaction = fragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.root_container, fragment)
                        fragmentTransaction?.addToBackStack(null)
                        fragmentTransaction?.commit()

                    })
                alertBuilder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                val alertDialog: AlertDialog = alertBuilder.create()
                alertDialog.show()
            }else {
                Toast.makeText(this.context, "Please complete the form", Toast.LENGTH_LONG).show();
            }
        }
    }
}