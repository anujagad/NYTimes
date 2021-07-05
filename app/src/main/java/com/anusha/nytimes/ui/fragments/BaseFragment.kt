package com.anusha.nytimes.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    protected var fragmentView: View? = null

    abstract val layoutId: Int
        @LayoutRes get

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = LayoutInflater.from(requireContext()).inflate(layoutId, container, false)
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        fragmentView = null
        super.onDestroy()
    }

    protected fun setOnSystemBackPressListener(code: () -> Unit) {
        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                code()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("BaseFragment2", "onDestroyView: ${this.javaClass.simpleName}", )
    }
}
