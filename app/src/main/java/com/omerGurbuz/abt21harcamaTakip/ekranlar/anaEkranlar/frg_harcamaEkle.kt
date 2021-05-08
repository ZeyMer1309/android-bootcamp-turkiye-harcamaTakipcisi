package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentHarcamaEkleBinding
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniHarcama
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniYapisi

class frg_harcamaEkle : Fragment() {

    private lateinit var binding: FragmentHarcamaEkleBinding
    private lateinit var vt: veritabaniHarcama

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHarcamaEkleBinding.inflate(layoutInflater, container, false)

        vt = veritabaniHarcama.veritabaniniAl(requireContext())

        binding.btnEkle.setOnClickListener {
            if(binding.tIHeAciklama.text.toString() == "")
                Toast.makeText(requireContext(),"Açıklama boş bırakılamaz!",Toast.LENGTH_LONG).show()
            else{
                if (binding.tIHeTutar.text.toString() == "")
                    Toast.makeText(requireContext(),"Tutar boş bırakılamaz!",Toast.LENGTH_LONG).show()
                else{

                    var harcamaTipi = when(binding.harcamaTuru.checkedRadioButtonId){
                        binding.htFatura.id -> 1
                        binding.htKira.id -> 2
                        binding.htEgitim.id -> 3
                        binding.htKulturSanat.id -> 4
                        else -> 5
                    }

                    var paraBirimi = when(binding.paraBirimi.checkedRadioButtonId){
                        binding.pbTRY.id -> 1
                        binding.pbGBP.id -> 2
                        binding.pbUSD.id -> 3
                        else -> 4
                    }

                    var aciklama = binding.tIHeAciklama.text.toString()
                    var tutar = binding.tIHeTutar.text.toString().toInt()

                    veriGirisi(aciklama,tutar,harcamaTipi,paraBirimi)
                    Toast.makeText(requireContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(frg_harcamaEkleDirections.actionFrgHarcamaEkleToFrgAnaEkran())
                }
            }
        }

        return binding.root
    }

    private fun veriGirisi(aciklama: String, tutar: Int, harcamaTipi: Int, paraBirimi: Int){
        val yeniHarcama = veritabaniYapisi(0,aciklama,tutar,harcamaTipi,paraBirimi)
        vt.veritabaniDAO().insert(yeniHarcama)
    }


}