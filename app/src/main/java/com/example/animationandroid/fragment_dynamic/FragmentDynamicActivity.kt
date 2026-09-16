package com.example.animationandroid.fragment_dynamic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityFragmentDynamicBinding

class FragmentDynamicActivity : AppCompatActivity() {

    companion object {
        const val ROOT_FRAGMENT_TAG: String = "root_fragment"
    }

    private var binding: ActivityFragmentDynamicBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFragmentDynamicBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadFragment(FragmentAFragment().getInstance("jignesh", 41), 0)

        binding?.btnFragA?.setOnClickListener {
            loadFragment(FragmentAFragment(),1)

        }

        binding?.btnFragB?.setOnClickListener {
            loadFragment(FragmentBFragment(),1)
        }

        binding?.btnFragC?.setOnClickListener {
            loadFragment(FragmentCFragment(),1)
        }
    }

    fun loadFragment(fragment: Fragment, flag: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

//        val bundle = Bundle()
//
//        bundle.putString("Arg1", "Raman")
//        bundle.putInt("Arg2", 7)
//
//        fragment.setArguments(bundle)

        if (flag == 0) {
            fragmentTransaction.add(R.id.container, fragment)
            fragmentManager.popBackStack(
                ROOT_FRAGMENT_TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            fragmentTransaction.addToBackStack(ROOT_FRAGMENT_TAG)
        } else {
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
        }


        fragmentTransaction.commit()
    }
}