package com.example.caloriecalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.caloriecalculator.databinding.BesinRecylerRowBinding
import com.example.caloriecalculator.model.Besin
import com.example.caloriecalculator.util.gorselIndir
import com.example.caloriecalculator.util.placeHolderYap
import com.example.caloriecalculator.view.BesinListesiDirections

class BesinRecylerAdapter(val besinListesi:ArrayList<Besin>):RecyclerView.Adapter<BesinRecylerAdapter.BesinViewHolder>() {

    class BesinViewHolder(val binding:BesinRecylerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding=BesinRecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiGuncelle(yeniBesinListesi:List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.isim.text=besinListesi[position].isim
        holder.binding.kalori.text=besinListesi[position].kalori

        holder.itemView.setOnClickListener {
            val action=BesinListesiDirections.actionBesinListesiToBesinDetay(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.gorselIndir(besinListesi[position].gorsel, placeHolderYap(holder.itemView.context))
    }

}