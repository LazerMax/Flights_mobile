package com.example.succulentus

import LoggingActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlightsActivity : LoggingActivity() {

    private lateinit var recyclerViewFlights: RecyclerView

    private val flightsList = mutableListOf<Flight>()
    private lateinit var flightsAdapter: FlightsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@FlightsActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        initViews()
        setupRecyclerView()
        loadFlightsData()
    }

    private fun initViews() {
        recyclerViewFlights = findViewById(R.id.recyclerViewFlights)
    }

    private fun setupRecyclerView() {
        flightsAdapter = FlightsAdapter(flightsList)
        recyclerViewFlights.layoutManager = LinearLayoutManager(this)
        recyclerViewFlights.adapter = flightsAdapter
    }

    private fun loadFlightsData() {
        flightsList.clear()
        flightsList.addAll(getSampleFlights())
        flightsAdapter.notifyDataSetChanged()
    }

    private fun getSampleFlights(): List<Flight> {
        return listOf(
            Flight("1", "SU-123", "Москва", "Санкт-Петербург", "10:00", "11:30"),
            Flight("2", "SU-456", "Санкт-Петербург", "Сочи", "14:20", "17:00"),
            Flight("3", "SU-789", "Новосибирск", "Москва", "08:45", "11:15"),
            Flight("4", "SU-101", "Екатеринбург", "Краснодар", "12:10", "15:40"),
            Flight("5", "SU-202", "Казань", "Минск", "09:30", "11:00")
        )
    }

    data class Flight(
        val id: String,
        val flightNumber: String,
        val departure: String,
        val destination: String,
        val departureTime: String,
        val arrivalTime: String
    )
}