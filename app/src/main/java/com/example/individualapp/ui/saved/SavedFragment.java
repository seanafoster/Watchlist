package com.example.individualapp.ui.saved;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.individualapp.AllMovieViewModel;
import com.example.individualapp.AppDatabase;
import com.example.individualapp.MovieDAO;
import com.example.individualapp.R;
import com.example.individualapp.Movie;
import com.example.individualapp.MovieRecyclerViewAdapter;
import com.example.individualapp.databinding.FragmentSavedBinding;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends DialogFragment {

    private FragmentSavedBinding binding;
    View root;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter trackRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSavedBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        toolbar = root.findViewById(R.id.savedToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.recyclerView);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            actionBar.show();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }

        Context context = getContext();
        trackRecyclerViewAdapter = new MovieRecyclerViewAdapter(new ArrayList<Movie>());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(trackRecyclerViewAdapter);
        recyclerView.setHasFixedSize(false);

        LiveData<List<Movie>> liveData = new ViewModelProvider(this)
                .get(AllMovieViewModel.class)
                .getCourseList(context);

        liveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    trackRecyclerViewAdapter.addItems(movies);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete_all:
                boolean confirmDelete = false;

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Confirmation")
                        .setMessage("Do you really want to delete all records?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppDatabase.getInstance(getContext())
                                                .movieDAO()
                                                .deleteAll();
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}