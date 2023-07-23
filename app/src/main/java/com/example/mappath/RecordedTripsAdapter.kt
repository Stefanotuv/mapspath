package com.example.mappath

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mappath.R
import com.example.mappath.RecordedTrip

// RecordedTripsAdapter.kt
class RecordedTripsAdapter(private val recordedTrips: List<RecordedTrip>) :
    RecyclerView.Adapter<RecordedTripsAdapter.RecordedTripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordedTripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip_banner, parent, false)

        // Set the width of the item to match the parent's width
        val layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams

        return RecordedTripViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordedTripViewHolder, position: Int) {
        val recordedTrip = recordedTrips[position]

        // Bind the trip details to the ViewHolder's views
        holder.tvTripName.text = recordedTrip.tripName
        holder.tvTripDate.text = "Date: ${recordedTrip.date}"
        holder.tvTripDistance.text = "Distance: ${recordedTrip.distance}"
        holder.tvTripDuration.text = "Duration: ${recordedTrip.duration}"

        // Handle click on the item to navigate to the details activity
        holder.itemView.setOnClickListener {
            // Start the TripDetailsActivity with the selected trip's locationList
            // You can pass the locationList as an intent extra or store it in a ViewModel
            // and retrieve it in the TripDetailsActivity
            val intent = Intent(it.context, TripDetailsActivity::class.java)
            intent.putParcelableArrayListExtra("locationList", ArrayList(recordedTrip.locationList))
            it.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return recordedTrips.size
    }

    class RecordedTripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Existing views
        val tvTripName: TextView = itemView.findViewById(R.id.tvTripName)

        // New views for trip details
        val tvTripDate: TextView = itemView.findViewById(R.id.tvTripDate)
        val tvTripDistance: TextView = itemView.findViewById(R.id.tvTripDistance)
        val tvTripDuration: TextView = itemView.findViewById(R.id.tvTripDuration)
    }
}
