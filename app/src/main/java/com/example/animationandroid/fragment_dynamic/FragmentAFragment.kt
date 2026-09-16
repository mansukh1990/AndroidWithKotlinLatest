package com.example.animationandroid.fragment_dynamic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animationandroid.databinding.FragmentABinding

class FragmentAFragment : Fragment() {

    companion object {
        private const val ARG1: String = "argument1"
        private const val ARG2: String = "argument2"
    }

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding

    fun getInstance(value1: String?, value2: Int): FragmentAFragment {
        val fragmentAFragment = FragmentAFragment()

        val bundle = Bundle()

        bundle.putString(ARG1, value1)
        bundle.putInt(ARG2, value2)

        fragmentAFragment.setArguments(bundle)

        return fragmentAFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentABinding.inflate(inflater, container, false)

        if (arguments != null) {
            val name = arguments?.getString(ARG1)
            val rollNo = arguments?.getInt(ARG2)


            Log.d("Values from activity", "Name is: $name")
            Log.d("Values from activity", "RollNo is: $rollNo")
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}