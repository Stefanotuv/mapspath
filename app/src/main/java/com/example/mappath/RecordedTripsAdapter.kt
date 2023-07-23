import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mappath.R
import com.example.mappath.RecordedTrip

class RecordedTripsAdapter(private val recordedTrips: List<RecordedTrip>) : RecyclerView.Adapter<RecordedTripsAdapter.RecordedTripViewHolder>() {

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
        // Bind other trip details as needed
    }

    override fun getItemCount(): Int {
        return recordedTrips.size
    }

    class RecordedTripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Customize the ViewHolder's views here (e.g., TextViews, ImageViews)
        val tvTripName: TextView = itemView.findViewById(R.id.tvTripName)
        // Add other views for trip details as needed
    }
}
