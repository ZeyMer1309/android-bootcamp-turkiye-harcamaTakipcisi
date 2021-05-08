package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.FragmentAnaEkranBinding
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniHarcama
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniYapisi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.DecimalFormat
import kotlin.math.roundToInt

class frg_anaEkran : Fragment() {

    private lateinit var binding: FragmentAnaEkranBinding
    private lateinit var viewModel : frg_anaEkran_viewModel
    //private val recyclerHarcamaAdapter = Harcama_rvAdapter(1,arrayListOf())
    private lateinit var recyclerHarcamaAdapter : Harcama_rvAdapter
    private lateinit var vt: veritabaniHarcama
    private lateinit var harcamaListesi: List<veritabaniYapisi>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dovizVeriListesi: ArrayList<Double>
    private lateinit var rvAdapter: Harcama_rvAdapter
    private var conversionRate = 0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnaEkranBinding.inflate(layoutInflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("com.omerGurbuz.abt21harcamatakipcisi", Context.MODE_PRIVATE)
        var degerVarMi = sharedPreferences.getString("kullaniciAdi", "Lütfen K. Adı Girin")
        val cinsiyetID = sharedPreferences.getInt("cinsiyet", -1)

        if (degerVarMi != "Lütfen K. Adı Girin")
            when(cinsiyetID)
            {
                1 -> degerVarMi += " Bey"
                0 -> degerVarMi += " Hanım"
                else -> degerVarMi = "Sayın ${degerVarMi}"
            }

        binding.kullaniciAdi.text = degerVarMi!!

        binding.kullaniciAdi.setOnClickListener{
            findNavController().navigate(frg_anaEkranDirections.actionFrgAnaEkranToFrgProfil())
        }

        binding.aeEkle.setOnClickListener{
            findNavController().navigate(frg_anaEkranDirections.actionFrgAnaEkranToFrgHarcamaEkle())
        }


        guncelKurDegerleriniAl("USD")
        guncelKurDegerleriniAl("TRY")
        guncelKurDegerleriniAl("GBP")
        baglantiVarMi()

        return binding.root
    }

    fun guncelKurDegerleriniAl(cevrilecekKur : String) {
        var API =
            "https://api.ratesapi.io/api/latest?base=EUR&symbols=$cevrilecekKur"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiResult = URL(API).readText()
                val jsonObject = JSONObject(apiResult)
                conversionRate =
                    jsonObject.getJSONObject("rates").getString(cevrilecekKur)
                        .toFloat()
                val preferences =
                    activity?.getSharedPreferences("com.omerGurbuz.abt21harcamatakipcisi", Context.MODE_PRIVATE)
                var editor = preferences?.edit()
                editor?.putInt(cevrilecekKur, conversionRate.roundToInt())
                editor?.apply()
                editor?.commit()

                withContext(Dispatchers.Main) {
                }
            } catch (e: Exception) {
                Log.e("Main", "$e")
            }
        }
    }

    private fun baglantiVarMi(){
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activateNetwork = cm.activeNetworkInfo
        val isConnected = activateNetwork != null && activateNetwork.isConnectedOrConnecting
        if (!isConnected)
            Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.baglantiYok), Snackbar.LENGTH_LONG).show()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(frg_anaEkran_viewModel::class.java)

        vt = veritabaniHarcama.veritabaniniAl(requireContext())
        harcamaListesi = vt.veritabaniDAO().getAllHarcama()
        dovizVeriListesi = ArrayList()
        sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)

        isDuzenleyici(sharedPreferences,harcamaListesi,dovizVeriListesi)

        binding.paraBirimiSecici.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener{group, checkedId ->
                renkleriSifirla()
                viewModel.getData()
                when (checkedId) {
                    binding.aeTRY.id -> {
                        Toast.makeText(requireContext(),"Türk Lirası",Toast.LENGTH_SHORT).show()
                        binding.aeTRY.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_renk_0))
                        sonTiklananiDegistir(sharedPreferences,1)
                        kurDegistir(1)
                    }
                    binding.aeGBP.id -> {
                        Toast.makeText(requireContext(),"Sterlin",Toast.LENGTH_SHORT).show()
                        binding.aeGBP.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_renk_0))
                        sonTiklananiDegistir(sharedPreferences,2)
                        kurDegistir(2)
                    }
                    binding.aeUSD.id -> {
                        Toast.makeText(requireContext(),"Amerikan Doları",Toast.LENGTH_SHORT).show()
                        binding.aeUSD.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_renk_0))
                        sonTiklananiDegistir(sharedPreferences,3)
                        kurDegistir(3)
                    }
                    else -> {
                        Toast.makeText(requireContext(),"Euro",Toast.LENGTH_SHORT).show()
                        binding.aeEUR.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_renk_0))
                        sonTiklananiDegistir(sharedPreferences,4)
                        kurDegistir(4)
                    }
                }
                //observeLiveData(sharedPreferences)
            })
        }

    private fun observeLiveData(sharedPreferences: SharedPreferences){
        viewModel.data.observe(viewLifecycleOwner, { data ->
            data.let {
                val editor = sharedPreferences.edit()
                editor.putFloat("TRY",it.rates.TRY.toFloat())
                editor.putFloat("GBP",it.rates.GBP.toFloat())
                editor.putFloat("USD",it.rates.USD.toFloat())
                editor.putFloat("EUR",it.rates.EUR.toFloat())
                editor.apply()
                binding.aeRvHarcamalar.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.loadingMessage.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.aeRvHarcamalar.visibility = View.INVISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.progressBar.visibility = View.GONE
                    binding.aeRvHarcamalar.visibility = View.VISIBLE
                    Snackbar.make(binding.root,"Güncel Veriler Çekilemedi.", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setTextColor(Color.WHITE)
                        .setBackgroundTint(Color.DKGRAY)
                        .setAction("Tamam"){}
                        .setActionTextColor(Color.RED)
                        .show()
                }
            }
        })
    }

    private fun renkleriSifirla(){
        binding.aeTRY.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_gri))
        binding.aeUSD.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_gri))
        binding.aeEUR.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_gri))
        binding.aeGBP.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_gri))
    }

    private fun sonTiklananiDegistir(sharedPreferences: SharedPreferences, sonTiklanan: Int){
        sharedPreferences.edit().putInt("sonTiklanan",sonTiklanan).apply()
    }

    @SuppressLint("SetTextI18n")
    private fun isDuzenleyici(sharedPreferences: SharedPreferences,
                              harcamaListesi: List<veritabaniYapisi>,
                              kurDegerleri: ArrayList<Double>) {

        val sonTiklanan = sharedPreferences.getInt("sonTiklanan",1)
        val veri_TRY = sharedPreferences.getFloat("TRY", 10.02F)
        val veri_GBP = sharedPreferences.getFloat("GBP", 0.87F)
        val veri_USD = sharedPreferences.getFloat("USD", 1.22F)
        val veri_EUR = sharedPreferences.getFloat("EUR", 1F)
        val x = sharedPreferences.getInt("number",0)

        kurDegerleri.clear()
        kurDegerleri.add(veri_TRY.toDouble())
        kurDegerleri.add(veri_GBP.toDouble())
        kurDegerleri.add(veri_USD.toDouble())
        kurDegerleri.add(veri_EUR.toDouble())

        when(sonTiklanan){
            1 -> {
                binding.aeTRY.setTextColor(ContextCompat.getColor(requireContext(), R.color.ana_renk_0))
                binding.aeTRY.isChecked = true
            }
            2 -> {
                binding.aeGBP.setTextColor(ContextCompat.getColor(requireActivity(),R.color.ana_renk_0))
                binding.aeGBP.isChecked = true
            }
            3 -> {
                binding.aeUSD.setTextColor(ContextCompat.getColor(requireActivity(),R.color.ana_renk_0))
                binding.aeUSD.isChecked = true
            }
            else -> {
                binding.aeEUR.setTextColor(ContextCompat.getColor(requireActivity(),R.color.ana_renk_0))
                binding.aeEUR.isChecked = true
            }
        }


        if(x == 0){
            rvAdapter = Harcama_rvAdapter(sonTiklanan,kurDegerleri)
            sharedPreferences.edit().putInt("number",1).apply()
        } else {
            rvAdapter = Harcama_rvAdapter(1,kurDegerleri)
        }

        rvAdapter.listeyiGuncelle(harcamaListesi)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.aeRvHarcamalar.layoutManager = layoutManager
        binding.aeRvHarcamalar.adapter = rvAdapter

        kurDegistir(sonTiklanan)
    }

    @SuppressLint("SetTextI18n")
    private fun kurDegistir(paraBirimi: Int){

        val adapter = Harcama_rvAdapter(paraBirimi,dovizVeriListesi)
        adapter.listeyiGuncelle(vt.veritabaniDAO().getAllHarcama())
        binding.aeRvHarcamalar.adapter = adapter
        val toplamHarcama = hesaplayici(paraBirimi,dovizVeriListesi,harcamaListesi)
        val sonuc = DecimalFormat("#.#").format(toplamHarcama)

        //var sonuc = 14.5
        when(paraBirimi){
            1 -> {binding.aeToplamHarcama.text = "$sonuc ₺"}
            2 -> {binding.aeToplamHarcama.text = "$sonuc £"}
            3 -> {binding.aeToplamHarcama.text = "$sonuc $"}
            else -> {binding.aeToplamHarcama.text = "$sonuc €"}
        }
    }

    private fun hesaplayici(kurTipi: Int, list: ArrayList<Double>, harcamaListesi: List<veritabaniYapisi>): Double{
        var toplamSonucu = 0.0
        for(i in harcamaListesi){
            when(kurTipi){
                1 -> {
                    when(i.paraBirimi){
                        1 -> {
                            toplamSonucu += i.tutar
                        }
                        2 -> {
                            val x = 1/list[1]
                            val value = x*list[0]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        3 -> {
                            val x = 1/list[2]
                            val value = x*list[0]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        4 -> {
                            val tl_1_kac_euro = 1*list[0]
                            val y = i.tutar * tl_1_kac_euro
                            toplamSonucu += y
                        }
                    }
                }
                2 -> {
                    when(i.paraBirimi){
                        1 -> {
                            val x = 1/list[0]
                            val value = x * list[1]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        2 -> {
                            toplamSonucu += i.tutar
                        }
                        3 -> {
                            val x = 1/list[2]
                            val value = x * list[1]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        4 -> {
                            val tl_1_kac_euro = 1*list[1]
                            val y = i.tutar * tl_1_kac_euro
                            toplamSonucu += y
                        }
                    }
                }
                3 -> {
                    when(i.paraBirimi){
                        1 -> {
                            val x = 1/list[0]
                            val value = x * list[2]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        2 -> {
                            val x = 1/list[1]
                            val value = x * list[2]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        3 -> {
                            toplamSonucu += i.tutar
                        }
                        4 -> {
                            val tl_1_kac_euro = 1*list[2]
                            val y = i.tutar * tl_1_kac_euro
                            toplamSonucu += y
                        }
                    }
                }
                4 -> {
                    when(i.paraBirimi){
                        1 -> {
                            val x = 1/list[0]
                            val value = x * list[3]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        2 -> {
                            val x = 1/list[1]
                            val value = x * list[3]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        3 -> {
                            val x = 1/list[2]
                            val value = x * list[3]
                            val total = i.tutar * value
                            toplamSonucu += total
                        }
                        4 -> {
                            toplamSonucu += i.tutar
                        }
                    }
                }
            }
        }
        return toplamSonucu
    }
}