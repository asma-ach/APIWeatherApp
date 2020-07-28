package com.example.openweathermapapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweathermapapp.R
import com.example.openweathermapapp.domain.datamodel.CountryModel
import com.example.openweathermapapp.presentation.adapters.CountryListAdapter
import com.example.openweathermapapp.presentation.viewmodel.TownListViewModel
import com.example.shared.data.model.City
import com.example.shared.data.WeatherInfoShowModel


/**
 * A simple [Fragment] subclass.
 * Use the [TownListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TownListFragment : Fragment() {

    private lateinit var model: WeatherInfoShowModel
    //private lateinit var viewModel: TownListViewModel

    private val viewModel by viewModels<TownListViewModel>()

    private var cityList: MutableList<City> = mutableListOf()
    private var countryModelList: MutableList<CountryModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_town_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.country_recyclerview)

        //viewModel = ViewModelProviders.of(this).get(TownListViewModel::class.java)

        val adapter = CountryListAdapter(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.cityListLiveData.observe(FirstFragment@this, object : Observer<MutableList<City>> {
            override fun onChanged(cities: MutableList<City>) {
                setCountryAdapter(cities, recyclerView, adapter)
            }
        })

        viewModel.cityListFailureLiveData.observe(FirstFragment@this, Observer { errorMessage ->
            Toast.makeText(FirstFragment@this.context, errorMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.getCityList(model)

        return view
    }

    private fun setCountryAdapter(cityList: MutableList<City>, recyclerView: RecyclerView, countryListAdapter : CountryListAdapter) {
        this.cityList = cityList
        this.cityList.forEach {
            var country = CountryModel(it.name, it.country)
            countryModelList.add(country)
        }

        countryListAdapter.setCountries(countryModelList)
    }

}