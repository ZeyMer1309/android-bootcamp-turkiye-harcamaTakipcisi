package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.omerGurbuz.abt21harcamaTakip.R
import com.omerGurbuz.abt21harcamaTakip.databinding.HarcamaRvRowBinding
import com.omerGurbuz.abt21harcamaTakip.veritabanı.veritabaniYapisi
import java.text.DecimalFormat

class Harcama_rvAdapter(private var paraBirimi: Int, val list: ArrayList<Double>) : RecyclerView.Adapter<Harcama_rvAdapter.rvHarcama_ViewHolder>() {

    private var harcamaListesi = emptyList<veritabaniYapisi>()
    class rvHarcama_ViewHolder(val itemBinding: HarcamaRvRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvHarcama_ViewHolder {
        val binding = HarcamaRvRowBinding.inflate(LayoutInflater.from(parent.context))
        return rvHarcama_ViewHolder(binding)
    }

    fun listeyiGuncelle(harcamalar : List<veritabaniYapisi>){
        this.harcamaListesi = harcamalar
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return harcamaListesi.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: rvHarcama_ViewHolder, position: Int) {
        val anlikNesne = harcamaListesi[position]
        val anlikFiyat = anlikNesne.tutar
        holder.itemBinding.rvId.text = anlikNesne.id.toString()
        holder.itemBinding.rvAciklama.text = anlikNesne.aciklama
        holder.itemBinding.rvTutar.text = anlikFiyat.toString()
        holder.itemBinding.rvResim.setImageResource(when(anlikNesne.harcamaTipi) {
            1 -> R.drawable.rv_fatura
            2 -> R.drawable.rv_kira
            3 -> R.drawable.rv_egitim
            4 -> R.drawable.rv_kultur_sanat
            else -> R.drawable.rv_diger
        })

        when(paraBirimi){
            1 -> {
                when(anlikNesne.paraBirimi){
                    1 -> {
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(anlikFiyat)} ₺"
                    }
                    2 -> {
                        val x = 1/list[1]
                        val value = x*list[0]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} ₺"
                    }
                    3 -> {
                        val x = 1/list[2]
                        val value = x*list[0]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} ₺"
                    }
                    4 -> {
                        val tl_1_kac_euro = 1*list[0]
                        val total = anlikFiyat * tl_1_kac_euro
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} ₺"
                    }
                }
            }
            2 -> {
                when(anlikNesne.paraBirimi){
                    1 -> {
                        val x = 1/list[0]
                        val value = x * list[1]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} £"
                    }
                    2 -> {
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(anlikFiyat)} £"
                    }
                    3 -> {
                        val x = 1/list[2]
                        val value = x * list[1]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} £"
                    }
                    4 -> {
                        val sterlin1_kac_euro = 1*list[1]
                        val total = anlikFiyat * sterlin1_kac_euro
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} £"
                    }
                }
            }
            3 -> {
                when(anlikNesne.paraBirimi){
                    1 -> {
                        val x = 1/list[0]
                        val value = x * list[2]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} $"
                    }
                    2 -> {
                        val x = 1/list[1]
                        val value = x * list[2]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} $"
                    }
                    3 -> {
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(anlikFiyat)} $"
                    }
                    4 -> {
                        val dolar1_kac_euro = 1*list[2]
                        val total = anlikFiyat * dolar1_kac_euro
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} $"
                    }
                }
            }
            4 -> {
                when(anlikNesne.paraBirimi){
                    1 -> {
                        val x = 1/list[0]
                        val value = x * list[3]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} €"
                    }
                    2 -> {
                        val x = 1/list[1]
                        val value = x * list[3]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} €"
                    }
                    3 -> {
                        val x = 1/list[2]
                        val value = x * list[3]
                        val total = anlikFiyat * value
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(total)} €"
                    }
                    4 -> {
                        holder.itemBinding.rvTutar.text = "${DecimalFormat("##.##").format(anlikFiyat)} €"
                    }
                }
            }
        }


        holder.itemView.setOnClickListener{
            val action = frg_anaEkranDirections.actionFrgAnaEkranToFrgHarcamaDetay(anlikNesne.id,holder.itemBinding.rvTutar.text.toString(),anlikNesne.aciklama,anlikNesne.harcamaTipi)
            Navigation.findNavController(it).navigate(action)
        }

    }
}