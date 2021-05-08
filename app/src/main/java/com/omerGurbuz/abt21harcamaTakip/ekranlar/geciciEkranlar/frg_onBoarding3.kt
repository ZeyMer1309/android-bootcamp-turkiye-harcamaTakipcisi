package com.omerGurbuz.abt21harcamaTakip.ekranlar.geciciEkranlar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentOnBoarding3Binding

class frg_onBoarding3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentOnBoarding3Binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)

        binding.obGec.setOnClickListener {
            findNavController().navigate(frg_onBoarding3Directions.actionFrgOnBoarding3ToFrgProfil())

            val sharedPreferences = requireActivity().getSharedPreferences("com.omerGurbuz.abt21harcamatakipcisi", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("tamamlandiMi",true).apply()
        }
        return binding.root
    }

}