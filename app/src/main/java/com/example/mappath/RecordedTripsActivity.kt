package com.example.mappath

import RecordedTripsAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecordedTripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorded_trips)

        val recordedTrips = getRecordedTrips() // Replace this with the function that retrieves recorded trips from storage or database

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecordedTripsAdapter(recordedTrips)

        // Set both horizontal and vertical spacing between the tiles (horizontal items)
        val horizontalSpaceInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_item_spacing)
        val verticalSpaceInPixels = resources.getDimensionPixelSize(R.dimen.vertical_item_spacing)
        recyclerView.addItemDecoration(SpaceItemDecoration(horizontalSpaceInPixels, verticalSpaceInPixels))
    }

    private fun getRecordedTrips(): List<RecordedTrip> {
        // Implement the logic to retrieve the recorded trips from storage or database
        // For demonstration purposes, let's assume there are some recorded trips available
        return listOf(
            RecordedTrip("Trip 1", "July 22, 2023", "2.5 km", "30 min"),
            RecordedTrip("Trip 2", "July 23, 2023", "3.1 km", "45 min"),
            RecordedTrip("Trip 3", "July 25, 2023", "4.2 km", "50 min")
            // Add more recorded trips as needed
        )
    }
}


