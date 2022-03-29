package com.example.money_tracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_onboarding.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DailyTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_daily_transaction, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DailyTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DailyTransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
//onboarding fragment
class OnboardingFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences //declaring shared preferences to store user data
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPreferences = this.requireActivity().getSharedPreferences("data",
            Context.MODE_PRIVATE
        )
        val firstInstall:Boolean=sharedPreferences.getBoolean("firstTime",false)
        val name:String?=sharedPreferences.getString("PName"," ")
        if(firstInstall)
        {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment2(name!!,1))
        }
        //making shared preferences with file name as data
        //creating an instance of shared preferences
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //val open:Int=sharedPreferences.getInt("open",1)
        //setting on click listener on the button in boarding fragment
        saveDetailsBtn.setOnClickListener(){
            var flag=0
            //getting the string entered by the user in edit text
            val userName=NameTextEdit.text.toString()
            val userBudget=BudgetTextEdit.text.toString()
            val userIncome=incomeTextEdit.text.toString()
            //condition when user did not entered the name then setting error
            if (TextUtils.isEmpty(userName)) {
                    NameTextEdit.error = "Please Enter Your Name"
                    NameTextEdit.requestFocus()
                    flag = 1
                }
                //condition when user did not entered the monthly budget then showing error
                else if (TextUtils.isEmpty(userBudget)) {
                    BudgetTextEdit.error = "Please Set Up Your Budget"
                    BudgetTextEdit.requestFocus()
                    flag = 1
                }
                //performing action on button click if name and budget are entered
                if (flag == 0) {

                    saveMydata(userName, userBudget, userIncome)//calling function
                    //code to go to the next fragment
                    findNavController().navigate(
                        OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment2(
                            userName,
                            1
                        )
                    )

                }
            }

    }
    //function to save data entered by user using shared preferences
    private fun saveMydata(userName: String, userBudget: String, userIncome: String)
    {
          with(sharedPreferences.edit())
          {
              putString("PName",userName)
              putString("PBudget",userBudget)
              putString("PIncome",userIncome)
              putInt("id",1)
              putBoolean("firstTime",true)

          }.apply()
    }


}