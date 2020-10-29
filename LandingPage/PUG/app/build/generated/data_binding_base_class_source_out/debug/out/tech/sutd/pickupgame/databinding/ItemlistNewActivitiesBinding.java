// Generated by view binder compiler. Do not edit!
package tech.sutd.pickupgame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import tech.sutd.pickupgame.R;

public final class ItemlistNewActivitiesBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ShapeableImageView clockImg;

  @NonNull
  public final MaterialTextView location;

  @NonNull
  public final ShapeableImageView locationImg;

  @NonNull
  public final MaterialTextView organizer;

  @NonNull
  public final ShapeableImageView organizerImg;

  @NonNull
  public final MaterialTextView sport;

  @NonNull
  public final ShapeableImageView sportImg;

  @NonNull
  public final MaterialTextView time;

  private ItemlistNewActivitiesBinding(@NonNull LinearLayout rootView, @NonNull CardView cardView,
      @NonNull ShapeableImageView clockImg, @NonNull MaterialTextView location,
      @NonNull ShapeableImageView locationImg, @NonNull MaterialTextView organizer,
      @NonNull ShapeableImageView organizerImg, @NonNull MaterialTextView sport,
      @NonNull ShapeableImageView sportImg, @NonNull MaterialTextView time) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.clockImg = clockImg;
    this.location = location;
    this.locationImg = locationImg;
    this.organizer = organizer;
    this.organizerImg = organizerImg;
    this.sport = sport;
    this.sportImg = sportImg;
    this.time = time;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemlistNewActivitiesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemlistNewActivitiesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.itemlist_new_activities, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemlistNewActivitiesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card-view;
      CardView cardView = rootView.findViewById(id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.clock_img;
      ShapeableImageView clockImg = rootView.findViewById(id);
      if (clockImg == null) {
        break missingId;
      }

      id = R.id.location;
      MaterialTextView location = rootView.findViewById(id);
      if (location == null) {
        break missingId;
      }

      id = R.id.location_img;
      ShapeableImageView locationImg = rootView.findViewById(id);
      if (locationImg == null) {
        break missingId;
      }

      id = R.id.organizer;
      MaterialTextView organizer = rootView.findViewById(id);
      if (organizer == null) {
        break missingId;
      }

      id = R.id.organizer_img;
      ShapeableImageView organizerImg = rootView.findViewById(id);
      if (organizerImg == null) {
        break missingId;
      }

      id = R.id.sport;
      MaterialTextView sport = rootView.findViewById(id);
      if (sport == null) {
        break missingId;
      }

      id = R.id.sport_img;
      ShapeableImageView sportImg = rootView.findViewById(id);
      if (sportImg == null) {
        break missingId;
      }

      id = R.id.time;
      MaterialTextView time = rootView.findViewById(id);
      if (time == null) {
        break missingId;
      }

      return new ItemlistNewActivitiesBinding((LinearLayout) rootView, cardView, clockImg, location,
          locationImg, organizer, organizerImg, sport, sportImg, time);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
