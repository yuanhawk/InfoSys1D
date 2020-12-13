package tech.sutd.pickupgame.ui.main.booking;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;
import java.util.Calendar;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.FragmentBookingBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.ui.BookingActivity;
import tech.sutd.pickupgame.ui.auth.viewmodel.UserViewModel;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.main.viewmodel.BookingActViewModel;
import tech.sutd.pickupgame.util.DateConverter;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class BookingFragment extends BaseFragment implements BaseInterface.CustomActListener {

    private int hour, min, hourEnd, minEnd, numberPicked, year, month, day;
    private String date;

    private User user;

    private BookingActViewModel bookingActViewModel;
    private UserViewModel userViewModel;

    private Calendar calendar;

    private FragmentBookingBinding binding;

    private Dialog dialog;

    @Inject SharedPreferences preferences;
    @Inject ViewModelProviderFactory providerFactory;

    @Override
    public void customAction() { // getActivityCache
        binding.sportSpinner.setText(preferences.getString(getString(R.string.select_sport), ""));
        binding.dateSpinner.setText(preferences.getString(getString(R.string.date), ""));
        binding.timeSpinnerStart.setText(preferences.getString(getString(R.string.start_time), ""));
        binding.timeSpinnerEnd.setText(preferences.getString(getString(R.string.end_time), ""));
        binding.locationSpinner.setText(preferences.getString(getString(R.string.select_location), ""));
        binding.participantSpinner.setText(preferences.getString(getString(R.string.select_num_participants), ""));
        binding.addNotesSpinner.setText(preferences.getString(getString(R.string.additional_notes), ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);

        bookingActViewModel = new ViewModelProvider(this, providerFactory).get(BookingActViewModel.class);
        userViewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);

        customAction();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeObserver();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        initSportSpinner();
        initDatePicker();
        initTimePicker();
        initLocationSpinner();
        initParticipantSpinner();
        initAddNotesSpinner();
        initConfirmBtn();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.dismiss();
    }

    private void subscribeObserver() {
        userViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users.size() > 0)
                user = users.get(0);
        });

        bookingActViewModel.observeBooking().observe(getViewLifecycleOwner(), bookingActivityResource -> {
            if (bookingActivityResource.status == Resource.Status.SUCCESS) {
                saveData(getString(R.string.select_sport), "");
                saveData(getString(R.string.date), "");
                saveData(getString(R.string.start_time), "");
                saveData(getString(R.string.end_time), "");
                saveData(getString(R.string.select_location), "");
                saveData(getString(R.string.select_num_participants), "");
                saveData(getString(R.string.additional_notes), "");

                saveData(getString(R.string.day), "");
                saveData(getString(R.string.month), "");
                saveData(getString(R.string.year), "");
                saveData(getString(R.string.hour), "00");
                saveData(getString(R.string.min), "00");
                saveData(getString(R.string.hour_end), "00");
                saveData(getString(R.string.min_end), "00");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initConfirmBtn() {
        binding.confirmButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binding.sportSpinner.getText())) {
                binding.sportSpinner.setError(getString(R.string.required_fields));
                return;
            }

            if (TextUtils.isEmpty(binding.dateSpinner.getText())) {
                binding.dateSpinner.setError(getString(R.string.required_fields));
                return;
            }

            if (TextUtils.isEmpty(binding.timeSpinnerStart.getText())) {
                binding.timeSpinnerStart.setError(getString(R.string.required_fields));
                return;
            }
            if (TextUtils.isEmpty(binding.timeSpinnerEnd.getText())) {
                binding.timeSpinnerEnd.setError(getString(R.string.required_fields));
                return;
            }

            if (TextUtils.isEmpty(binding.locationSpinner.getText())) {
                binding.locationSpinner.setError(getString(R.string.required_fields));
                return;
            }

            if (TextUtils.isEmpty(binding.participantSpinner.getText())) {
                binding.participantSpinner.setError(getString(R.string.required_fields));
                return;
            }

            String startTime = DateConverter.epochConverter(
                    preferences.getString(getString(R.string.date), ""),
                    preferences.getString(getString(R.string.hour), ""),
                    preferences.getString(getString(R.string.min), ""));
            String endTime = DateConverter.epochConverter(
                    preferences.getString(getString(R.string.date), ""),
                    preferences.getString(getString(R.string.hour_end), ""),
                    preferences.getString(getString(R.string.min_end), ""));

            if (Long.parseLong(startTime) >= Long.parseLong(endTime)) {
                binding.timeSpinnerStart.setError(getString(R.string.time_error));
                binding.timeSpinnerEnd.setError(getString(R.string.time_error));
                return;
            } else if (Long.parseLong(startTime) < Calendar.getInstance().getTimeInMillis()) {
                binding.dateSpinner.setError(getString(R.string.date_error));
                binding.timeSpinnerStart.setError(getString(R.string.start_time_error));
                return;
            }

            bookingActViewModel.push(new BookingActivity.Builder()
                    .setSport(preferences.getString(getString(R.string.select_sport), ""))
                    .setEpoch(DateConverter.epochConverter(
                            preferences.getString(getString(R.string.date), ""),
                            preferences.getString(getString(R.string.hour), ""),
                            preferences.getString(getString(R.string.min), "")))
                    .setEpochEnd(DateConverter.epochConverter(
                            preferences.getString(getString(R.string.date), ""),
                            preferences.getString(getString(R.string.hour_end), ""),
                            preferences.getString(getString(R.string.min_end), "")))
                    .setLocation(preferences.getString(getString(R.string.select_location), ""))
                    .setParticipant(preferences.getString(getString(R.string.select_num_participants), ""))
                    .setCount(String.valueOf(0))
                    .setNotes(preferences.getString(getString(R.string.additional_notes), ""))
                    .setOrganizer(user.getName())
                    .build()
            );

        });
    }

    private void initAddNotesSpinner() {
        binding.addNotesSpinner.setOnClickListener(v -> {
            dialog = setDialog(R.layout.addon_notes);

            EditText add_notes_et = dialog.findViewById(R.id.add_notes);
            add_notes_et.setText(preferences.getString(getString(R.string.additional_notes), ""));

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> {
                String addNotes = String.valueOf(add_notes_et.getText()).trim();
                binding.addNotesSpinner.setText(addNotes);

                saveData(getString(R.string.additional_notes), addNotes);

                binding.addNotesSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void initParticipantSpinner() {
        binding.participantSpinner.setOnClickListener(v -> {
            dialog = setDialog(R.layout.number_picker);

            NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);

            numberPicker.setMinValue(2);
            numberPicker.setMaxValue(20);

            numberPicker.setWrapSelectorWheel(true);

            if (!preferences.getString(getString(R.string.select_num_participants), "").equals(""))
                try {
                    numberPicked = Integer.parseInt(preferences.getString(getString(R.string.select_num_participants), ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            else
                numberPicked = 2;
            numberPicker.setValue(numberPicked);

            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> numberPicked = newVal);

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> {
                String numOfParticipants = String.valueOf(numberPicked);
                binding.participantSpinner.setText(numOfParticipants);

                saveData(getString(R.string.select_num_participants), numOfParticipants);

                binding.participantSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void initLocationSpinner() {
        binding.locationSpinner.setOnClickListener(v -> {
            dialog = setDialog(R.layout.spinner);

            MaterialTextView title = dialog.findViewById(R.id.title);
            EditText location_et = dialog.findViewById(R.id.search_category);
            ListView listView = dialog.findViewById(R.id.category_list);

            title.setText(getString(R.string.select_location));

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
                String location = adapter.getItem(position);
                binding.locationSpinner.setText(location);

                saveData(getString(R.string.select_location), location);

                binding.locationSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void initTimePicker() {
        binding.timeSpinnerStart.setOnClickListener(v -> {
            dialog = setDialog(R.layout.time_picker);

            TimePicker timePicker = dialog.findViewById(R.id.time_picker);

            calendar = Calendar.getInstance();

            if (!preferences.getString(getString(R.string.start_time), "").equals("")) {
                try {
                    hour = Integer.parseInt(preferences.getString(getString(R.string.hour), String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))));
                    min = Integer.parseInt(preferences.getString(getString(R.string.min), String.valueOf(calendar.get(Calendar.MINUTE))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
            }

            timePicker.setHour(hour);
            timePicker.setMinute(min);

            timePicker.setOnTimeChangedListener((timeView, hourOfDay, minute) -> {
                hour = hourOfDay;
                min = minute;
            });

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> {
                String timeFormat = DateConverter.timeConverter(hour, min);
                binding.timeSpinnerStart.setText(timeFormat);

                saveData(getString(R.string.start_time), timeFormat);
                saveData(getString(R.string.hour), String.valueOf(hour));
                saveData(getString(R.string.min), String.valueOf(min));

                binding.timeSpinnerStart.setError(null);

                dialog.dismiss();
            });
        });

        binding.timeSpinnerEnd.setOnClickListener(v -> {
            dialog = setDialog(R.layout.time_picker);

            TimePicker timePicker = dialog.findViewById(R.id.time_picker);

            calendar = Calendar.getInstance();

            if (hour != 0 || preferences.getString(getString(R.string.end_time), "").equals("")) {
                try {
                    hourEnd = Integer.parseInt(preferences.getString(getString(R.string.hour), String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))) + 2;
                    minEnd = Integer.parseInt(preferences.getString(getString(R.string.min), String.valueOf(calendar.get(Calendar.MINUTE))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    hourEnd = Integer.parseInt(preferences.getString(getString(R.string.hour_end), String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))));
                    minEnd = Integer.parseInt(preferences.getString(getString(R.string.min_end), String.valueOf(calendar.get(Calendar.MINUTE))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            timePicker.setHour(hourEnd);
            timePicker.setMinute(minEnd);

            timePicker.setOnTimeChangedListener((timeView, hourOfDay, minute) -> {
                hourEnd = hourOfDay;
                minEnd = minute;
            });

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> {
                String timeFormat = DateConverter.timeConverter(hourEnd, minEnd);
                binding.timeSpinnerEnd.setText(timeFormat);

                saveData(getString(R.string.end_time), timeFormat);
                saveData(getString(R.string.hour_end), String.valueOf(hourEnd));
                saveData(getString(R.string.min_end), String.valueOf(minEnd));

                binding.timeSpinnerEnd.setError(null);

                dialog.dismiss();
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    private void initDatePicker() {
        binding.dateSpinner.setOnClickListener(v -> {
            dialog = setDialog(R.layout.date_picker);

            DatePicker datePicker = dialog.findViewById(R.id.date_picker);

            calendar = Calendar.getInstance();

            if (!preferences.getString(getString(R.string.date), "").equals("")) {
                try {
                    day = Integer.parseInt(preferences.getString(getString(R.string.day), ""));
                    month = Integer.parseInt(preferences.getString(getString(R.string.month), "")) - 1;
                    year = Integer.parseInt(preferences.getString(getString(R.string.year), ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }

            datePicker.updateDate(year, month, day);

            datePicker.init(year, month, day, (dateView, year, monthOfYear, dayOfMonth) -> {
                this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
            });

            dialog.findViewById(R.id.confirm_button).setOnClickListener(view -> {
                date = DateConverter.formatDate(day, month + 1, year);
                binding.dateSpinner.setText(date);

                saveData(getString(R.string.date), date);
                saveData(getString(R.string.day), String.valueOf(day));
                saveData(getString(R.string.month), String.valueOf(month + 1));
                saveData(getString(R.string.year), String.valueOf(year));

                binding.dateSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void initSportSpinner() {
        binding.sportSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.spinner);

            MaterialTextView title = dialog.findViewById(R.id.title);
            EditText sport_et = dialog.findViewById(R.id.search_category);
            ListView listView = dialog.findViewById(R.id.category_list);

            title.setText(getString(R.string.select_sport));

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
                String sport = adapter.getItem(position);

                binding.sportSpinner.setText(sport);

                saveData(getString(R.string.select_sport), sport);

                binding.sportSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void saveData(String key, String val) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, val);
        editor.apply();
    }
}