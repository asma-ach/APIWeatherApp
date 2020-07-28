package com.example.openweathermapapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.openweathermapapp.R
import com.example.openweathermapapp.presentation.viewmodel.WeatherInfoViewModel
import com.example.shared.data.WeatherInfoShowModel
import com.example.shared.data.WeatherInfoShowModelImpl
import com.example.shared.data.model.City
import com.example.shared.data.model.WeatherData
import com.example.shared.utils.convertToListOfCityName
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.layout_input_part.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherInfoFragment : Fragment() {

    private lateinit var model: WeatherInfoShowModel
    //private lateinit var viewModel: WeatherInfoViewModel
    private val viewModel by viewModels<WeatherInfoViewModel>()

    private var cityList: MutableList<City> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        model = activity?.baseContext?.let {
            WeatherInfoShowModelImpl(
                it
            )
        }!!
        // initialize ViewModel
        //viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)

        setLiveDataListeners()
        //setViewClickListener()

        /**
         * get the city list from city_list.json
         */
        viewModel.getCityList(model)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_view_weather).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_TownListFragment)
        }

        view.findViewById<Spinner>(R.id.spinner).onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedCityCountry = cityList[position].name + "," + cityList[position].iso2
                viewModel.getWeatherInfo(selectedCityCountry, model) // fetch weather info
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun setLiveDataListeners() {
        viewModel.cityListLiveData.observe(FirstFragment@this, object : Observer<MutableList<City>> {
            override fun onChanged(cities: MutableList<City>) {
                setCityListSpinner(cities)
            }
        })

        viewModel.cityListFailureLiveData.observe(FirstFragment@this, Observer { errorMessage ->
            Toast.makeText(FirstFragment@this.context, errorMessage, Toast.LENGTH_LONG).show()
        })

        /**
         * This livedata will handle the ProgressBar visibility
         */
        viewModel.progressBarLiveData.observe(FirstFragment@this, Observer { isShowLoader ->
            if (isShowLoader)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })

        /**
         * This method will be triggered when we successfully get Weather data from the weather api
         */
        viewModel.weatherInfoLiveData.observe(FirstFragment@this, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })


        viewModel.weatherInfoFailureLiveData.observe(FirstFragment@this, Observer { errorMessage ->
            output_group.visibility = View.GONE
            tv_error_message.visibility = View.VISIBLE
            tv_error_message.text = errorMessage
        })
    }

    private fun setCityListSpinner(cityList: MutableList<City>) {
        this.cityList = cityList

        val arrayAdapter = FirstFragment@this.context?.let {
            ArrayAdapter(
                it,
                R.layout.spinner_city_item_layout,
                this.cityList.convertToListOfCityName()
            )
        }

        spinner.adapter = arrayAdapter
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        tv_date_time?.text = weatherData.dateTime
        tv_temperature?.text = weatherData.temperature
        tv_city_country?.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(iv_weather_condition)
        tv_weather_condition?.text = weatherData.weatherConditionIconDescription

        tv_humidity_value?.text = weatherData.humidity
        tv_pressure_value?.text = weatherData.pressure
        tv_visibility_value?.text = weatherData.visibility

        tv_sunrise_time?.text = weatherData.sunrise
        tv_sunset_time?.text = weatherData.sunset
    }
}