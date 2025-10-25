package com.example.succulentus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlightsAdapter(
    private val flights: List<FlightsActivity.Flight>
) : RecyclerView.Adapter<FlightsAdapter.FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]
        holder.textFlightNumber.text = flight.flightNumber
        holder.textRoute.text = "${flight.departure} → ${flight.destination}"
        holder.textTime.text = "${flight.departureTime} - ${flight.arrivalTime}"
    }

    override fun getItemCount(): Int = flights.size

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textFlightNumber: TextView = itemView.findViewById(R.id.textFlightNumber)
        val textRoute: TextView = itemView.findViewById(R.id.textRoute)
        val textTime: TextView = itemView.findViewById(R.id.textTime)
    }
}