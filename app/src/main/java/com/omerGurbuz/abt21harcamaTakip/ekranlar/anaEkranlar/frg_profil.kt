package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentProfilBinding

class frg_profil : Fragment() {

    /*

    Hadi hoca derste gereksiz yere ViewModel kullanmamamızı söylediği için sharedPreferences
    ile veriyi kaydederek ana sayfadan almasını sağladım.

     */

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = requireActivity().getSharedPreferences(
            "com.omerGurbuz.abt21harcamatakipcisi",
            Context.MODE_PRIVATE
        )

        val binding = FragmentProfilBinding.inflate(layoutInflater, container, false)

        val degerVarMi = sharedPreferences.getString("kullaniciAdi", null)
        val cinsiyetID = sharedPreferences.getInt("cinsiyet", -1)

        if (degerVarMi == null)
            sharedPreferences.edit().putString("kullaniciAdi", "Lütfen K. Adı Girin").apply()

        degerVarMi?.let {
            if (degerVarMi != "Lütfen K. Adı Girin")
            {
                binding.tIYeniKullaniciAdi.setText(degerVarMi)
                when(cinsiyetID){
                    1 -> binding.rbErkek.isChecked = true
                    0 -> binding.rbKadin.isChecked = true
                    else -> binding.rbDiger.isChecked = true
                }
            }
        }


        binding.btnKaydet.setOnClickListener {
            if (binding.tIYeniKullaniciAdi.text.toString() == "") {
                var kullanici = ""

                Toast.makeText(
                    requireActivity(),
                    "Lütfen isminizi girin. ${kullanici}",
                    Toast.LENGTH_LONG
                ).show()
            } else if (binding.tIYeniKullaniciAdi.text.toString() != "") {

                var kullaniciAdi = binding.tIYeniKullaniciAdi.text.toString()

                when (binding.rbCinsiyetSecici.checkedRadioButtonId) {
                    binding.rbErkek.id -> sharedPreferences.edit().putInt("cinsiyet", 1).apply()
                    binding.rbKadin.id -> sharedPreferences.edit().putInt("cinsiyet", 0).apply()
                    else -> sharedPreferences.edit().putInt("cinsiyet", -1).apply()
                }

                //Toast.makeText(requireActivity(), kullaniciAdi, Toast.LENGTH_LONG).show()

                sharedPreferences.edit().putString("kullaniciAdi", kullaniciAdi).apply()
                findNavController().navigate(frg_profilDirections.actionFrgProfilToFrgAnaEkran())
            }
        }

        return binding.root
    }
}