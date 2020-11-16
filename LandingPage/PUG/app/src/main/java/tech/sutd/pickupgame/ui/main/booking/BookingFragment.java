package tech.sutd.pickupgame.ui.main.booking;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
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

import tech.sutd.pickupgame.BaseApplication;
import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentBookingBinding;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.MainActivity;
import tech.sutd.pickupgame.util.DateConverter;

public class BookingFragment extends BaseFragment implements BaseInterface {

    private int hour, min, numberPicked, year, month, day;

    private FragmentBookingBinding binding;

    private NavController navController;

    private Calendar calendar;

    private SharedPreferences preferences;

    private BaseInterface listener;

    @Override
    public void customAction() { // getActivityCache
        binding.sportSpinner.setText(preferences.getString(getString(R.string.select_sport), ""));
        binding.dateSpinner.setText(preferences.getString(getString(R.string.date), ""));
        binding.timeSpinner.setText(preferences.getString(getString(R.string.time), ""));
        binding.locationSpinner.setText(preferences.getString(getString(R.string.select_location), ""));
        binding.participantSpinner.setText(preferences.getString(getString(R.string.select_num_participants), ""));
        binding.addNotesSpinner.setText(preferences.getString(getString(R.string.additional_notes), ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);

        preferences = BaseApplication.getSharedPref();
        customAction();

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
        initSportSpinner();
        initDatePicker();
        initTimePicker();
        initLocationSpinner();
        initParticipantSpinner();
        initAddNotesSpinner();
        initConfirmBtn();
    }

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

            if (TextUtils.isEmpty(binding.timeSpinner.getText())) {
                binding.timeSpinner.setError(getString(R.string.required_fields));
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

            if (TextUtils.isEmpty(binding.addNotesSpinner.getText())) {
                binding.addNotesSpinner.setError(getString(R.string.required_fields));
                return;
            }

            saveData(getString(R.string.select_sport), "");
            saveData(getString(R.string.date), "");
            saveData(getString(R.string.time), "");
            saveData(getString(R.string.select_location), "");
            saveData(getString(R.string.select_num_participants), "");
            saveData(getString(R.string.additional_notes), "");

            listener.customAction(); // set ProgressBar on
        });
    }

    private void initAddNotesSpinner() {
        binding.addNotesSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.addon_notes);

            EditText add_notes_et = dialog.findViewById(R.id.add_notes);
            Button button = dialog.findViewById(R.id.confirm_button);

            button.setOnClickListener(view -> {
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
            Dialog dialog = setDialog(R.layout.number_picker);

            NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);
            Button button = dialog.findViewById(R.id.confirm_button);

            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(20);

            numberPicker.setWrapSelectorWheel(true);

            numberPicked = 1;
            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> numberPicked = newVal);

            button.setOnClickListener(view -> {
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
                String location = adapter.getItem(position);
                binding.locationSpinner.setText(location);

                saveData(getString(R.string.select_location), location);

                binding.locationSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

    private void initTimePicker() {
        binding.timeSpinner.setOnClickListener(v -> {
            Dialog dialog = setDialog(R.layout.time_picker);

            TimePicker timePicker = dialog.findViewById(R.id.time_picker);
            Button button = dialog.findViewById(R.id.confirm_button);

            button.setOnClickListener(view -> {
                calendar = Calendar.getInstance();

                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                timePicker.setOnTimeChangedListener((timeView, hourOfDay, minute) -> {
                    hour = hourOfDay;
                    min = minute;
                });

                String timeFormat = DateConverter.timeConverter(binding, hour, min);

                saveData(getString(R.string.time), timeFormat);

                binding.timeSpinner.setError(null);

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

            button.setOnClickListener(view -> {
                calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePicker.init(year, month, day, (dateView, year, monthOfYear, dayOfMonth) -> {
                    this.year = year;
                    month = monthOfYear + 1;
                    day = dayOfMonth;
                });

                String date = day + "/" + month + "/" + year;
                binding.dateSpinner.setText(date);

                saveData(getString(R.string.date), date);

                binding.dateSpinner.setError(null);

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
                String sport = adapter.getItem(position);

                binding.sportSpinner.setText(sport);

                saveData(getString(R.string.select_sport), sport);

                binding.sportSpinner.setError(null);

                dialog.dismiss();
            });
        });
    }

//    private Dialog setDialog(int layout) {
//        Dialog dialog = new Dialog(getContext());
//
//        dialog.setContentView(layout);
//
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//
//        dialog.getWindow().setAttributes(params);
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dialog.show();
//
//        return dialog;
//    }

    private void saveData(String key, String val) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BaseInterface) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}