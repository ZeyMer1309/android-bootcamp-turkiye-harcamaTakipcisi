package com.omerGurbuz.abt21harcamaTakip.ekranlar.geciciEkranlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentOnBoarding2Binding

class frg_onBoarding2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentOnBoarding2Binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)

        binding.obGec.setOnClickListener {
            findNavController().navigate(frg_onBoarding2Directions.actionFrgOnBoarding2ToFrgOnBoarding3())

        }
        return binding.root
    }
}