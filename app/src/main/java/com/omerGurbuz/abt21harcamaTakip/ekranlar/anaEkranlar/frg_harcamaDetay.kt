package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentHarcamaDetayBinding
import com.omerGurbuz.abt21harcamaTakip.ekranlar.BaseFragment
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniHarcama
import kotlinx.coroutines.launch

class frg_harcamaDetay : BaseFragment() {

    private lateinit var binding : FragmentHarcamaDetayBinding
    private lateinit var vt: veritabaniHarcama

    private var harcamaID = 0
    private var gelenTutar = ""
    private var aciklama = ""
    private var harcamaTipi = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHarcamaDetayBinding.inflate(layoutInflater, container, false)

        vt = veritabaniHarcama.veritabaniniAl(requireContext())

        arguments?.let {
            harcamaID = frg_harcamaDetayArgs.fromBundle(it).harcamaID
            gelenTutar = frg_harcamaDetayArgs.fromBundle(it).anlikTutar
            aciklama = frg_harcamaDetayArgs.fromBundle(it).aciklama
            harcamaTipi = frg_harcamaDetayArgs.fromBundle(it).harcamaTipi
        }

        binding.harcamaDetayID.text = harcamaID.toString()
        binding.harcamaDetayAciklamasi.text = aciklama
        binding.harcamaDetayTutari.text = gelenTutar
        binding.harcamaDetaySimgesi.setImageResource(when(harcamaTipi){
            1 -> R.drawable.rv_fatura
            2 -> R.drawable.rv_kira
            3 -> R.drawable.rv_egitim
            4 -> R.drawable.rv_kultur_sanat
            else -> R.drawable.rv_diger
        })

        binding.harcamaDetaySil.setOnClickListener {
            val silinecek = vt.veritabaniDAO().getHarcama(harcamaID)
            vt.veritabaniDAO().deleteHarcama(silinecek)

            findNavController().navigate(frg_harcamaDetayDirections.actionFrgHarcamaDetayToFrgAnaEkran())
        }

        return binding.root
    }

}