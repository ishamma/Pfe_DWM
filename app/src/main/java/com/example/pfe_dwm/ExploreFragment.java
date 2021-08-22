package com.example.pfe_dwm;

import android.content.DialogInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView dateTextView;
    private CheckBox modeDarkDate;
    private CheckBox modeCustomAccentDate;
    private CheckBox vibrateDate;
    private CheckBox dismissDate;
    private CheckBox titleDate;
    private CheckBox showYearFirst;
    private CheckBox showVersion2;
    private CheckBox switchOrientation;
    private CheckBox limitSelectableDays;
    private CheckBox highlightDays;
    private CheckBox defaultSelection;
    private DatePickerDialog dpd;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatePickerDialog datePickerDialog ;

    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }






    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(new DateValidatorWeekdays());
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
       builder.setSelection(today);

       builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        EditText date = view.findViewById(R.id.in_date);
        Button btndate = view.findViewById(R.id.btn_date);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {

            @Override
            public void onPositiveButtonClick(Long selection) {

                date.setText(materialDatePicker.getHeaderText());

            }
        });





        return view;




    }
}