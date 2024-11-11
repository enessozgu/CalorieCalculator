package com.example.caloriecalculator.view

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caloriecalculator.adapter.BesinRecylerAdapter
import com.example.caloriecalculator.databinding.FragmentBesinListesiBinding
import com.example.caloriecalculator.model.Besin
import com.example.caloriecalculator.service.BesinAPI
import com.example.caloriecalculator.viewmodel.BesinListesiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BesinListesi : Fragment() {
    private var _binding: FragmentBesinListesiBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BesinListesiViewModel
    private val besinRecylerAdapter=BesinRecylerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListesiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel=ViewModelProvider(this)[BesinListesiViewModel::class.java]
            viewModel.refreshData()

            binding.besinRecyle.layoutManager=LinearLayoutManager(requireContext())
            binding.besinRecyle.adapter=besinRecylerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinRecyle.visibility=View.GONE
            binding.textView.visibility=View.GONE
            binding.progressBar.visibility=View.VISIBLE

            viewModel.verileriInternettenAl()

            binding.swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }



    private fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner){
            besinRecylerAdapter.besinListesiGuncelle(it)
            binding.besinRecyle.visibility=View.VISIBLE
        }


        viewModel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){

                binding.textView.visibility=View.VISIBLE
                binding.besinRecyle.visibility=View.GONE

            }else{
                binding.textView.visibility=View.GONE
            }
        }

        viewModel.besinYukleniyor.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility=View.VISIBLE
                binding.besinRecyle.visibility=View.GONE
                binding.textView.visibility=View.GONE
            }else{
                binding.progressBar.visibility=View.GONE
            }
        }

    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}