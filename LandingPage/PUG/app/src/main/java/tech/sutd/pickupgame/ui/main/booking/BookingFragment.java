package tech.sutd.pickupgame.ui.main.booking;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentBookingBinding;
import tech.sutd.pickupgame.util.TextInputEditTextNoAutofill;

public class BookingFragment extends BaseFragment {

    private int hour, min, numberPicked, year, month, day;

    private FragmentBookingBinding binding;

    private NavController navController;

    private Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        calendar = Calendar.getInstance();

        initSportSpinner();
        initDatePicker();
        initTimePicker();
        initLocationSpinner();
        initParticipantSpinner();
        initAddNotesSpinner();
    }

    private void initAddNotesSpinner() {
        binding.addNotesSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.addon_notes);

            EditText add_notes_et = dialog.findViewById(R.id.add_notes);
            Button button = dialog.findViewById(R.id.confirm_button);

            button.setOnClickListener(view -> {
                binding.addNotesSpinner.setText(String.valueOf(add_notes_et.getText()));

                dialog.dismiss();
            });
        });
    }

    private void initParticipantSpinner() {
        binding.participantSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.number_picker);

            NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);
            Button button = dialog.findViewById(R.id.confirm_button);

            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(20);

            numberPicker.setWrapSelectorWheel(true);

            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> numberPicked = newVal);

            button.setOnClickListener(view -> {
                binding.participantSpinner.setText(String.valueOf(numberPicked));

                dialog.dismiss();
            });
        });
    }

    private void initLocationSpinner() {
        binding.locationSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.spinner);

            EditText location_et = dialog.findViewById(R.id.search_category);
            ListView listView = dialog.findViewById(R.id.category_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.location_array))
            );

            listView.setAdapter(adapter);

            location_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener((parent, view, position, id) -> {
                binding.locationSpinner.setText(adapter.getItem(position));

                dialog.dismiss();
            });
        });
    }

    @SuppressLint("DefaultLocale")
    private void initTimePicker() {
        binding.timeSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.time_picker);

            TimePicker timePicker = dialog.findViewById(R.id.time_picker);
            Button button = dialog.findViewById(R.id.confirm_button);

            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);

            timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
                hour = hourOfDay;
                min = minute;
            });

            button.setOnClickListener(view -> {
                String time;

                if (hour < 12)
                    time = "AM";
                else if (hour == 12)
                    time = "PM";
                else {
                    time = "PM";
                    hour -= 12;
                }

                if (min < 10)
                    binding.timeSpinner.setText(String.format("%d : 0%d %s", hour, min, time));
                else
                    binding.timeSpinner.setText(String.format("%d : %d %s", hour, min, time));

                dialog.dismiss();
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    private void initDatePicker() {
        binding.dateSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.date_picker);

            DatePicker datePicker = dialog.findViewById(R.id.date_picker);
            Button button = dialog.findViewById(R.id.confirm_button);

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);

            datePicker.init(year, month, day, (view, year, monthOfYear, dayOfMonth) -> {
                this.year = year;
                month = monthOfYear + 1;
                day = dayOfMonth;
            });

            button.setOnClickListener(view -> {
                binding.dateSpinner.setText(new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year));

                dialog.dismiss();
            });
        });
    }

    private void initSportSpinner() {
        binding.sportSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.spinner);

            EditText sport_et = dialog.findViewById(R.id.search_category);
            ListView listView = dialog.findViewById(R.id.category_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.sport_array))
            );

            listView.setAdapter(adapter);

            sport_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener((parent, view, position, id) -> {
                binding.sportSpinner.setText(adapter.getItem(position));

                dialog.dismiss();
            });
        });
    }

    private Dialog setDialog(int layout) {
        Dialog dialog = new Dialog(getContext());

        dialog.setContentView(layout);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        return dialog;
    }
}