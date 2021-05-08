package com.omerGurbuz.abt21harcamaTakip.ekranlar.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.ActivityMainBinding
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import kotlin.math.roundToInt

class frg_splashScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        uygulamayiBaslat()
        return binding.root
    }





    fun uygulamayiBaslat()
    {
        val sharedPreferences = requireActivity().getSharedPreferences("com.omerGurbuz.abt21harcamatakipcisi", Context.MODE_PRIVATE)
        val tamamlandiMi = sharedPreferences.getBoolean("tamamlandiMi", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if(tamamlandiMi)
                findNavController().navigate(frg_splashScreenDirections.actionFrgSplashScreenToFrgAnaEkran())
            else
                findNavController().navigate(frg_splashScreenDirections.actionFrgSplashScreenToFrgOnBoarding1())
        }, 4000)
    }

}