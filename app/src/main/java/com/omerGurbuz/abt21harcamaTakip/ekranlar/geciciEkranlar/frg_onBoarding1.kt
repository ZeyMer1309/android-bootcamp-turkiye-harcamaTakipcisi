package com.omerGurbuz.abt21harcamaTakip.ekranlar.geciciEkranlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentOnBoarding1Binding

class frg_onBoarding1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentOnBoarding1Binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)

        binding.obGec.setOnClickListener {
            findNavController().navigate(frg_onBoarding1Directions.actionFrgOnBoarding1ToFrgOnBoarding2())
        }
        return binding.root
    }
}