package com.example.mappath

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecordedTripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorded_trips)

        val recordedTrips = getRecordedTrips()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecordedTripsAdapter(recordedTrips)
    }

    private fun getRecordedTrips(): List<RecordedTrip> {
        return listOf(
            RecordedTrip("Trip 1", "July 22, 2023", "2.5 km", "30 min", emptyList()),
            RecordedTrip("Trip 2", "July 23, 2023", "3.1 km", "45 min", emptyList()),
            RecordedTrip("Trip 3", "July 25, 2023", "4.2 km", "50 min", emptyList())
        )
    }
}
