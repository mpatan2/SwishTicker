package com.hinterlong.kevin.swishticker.ui.modules;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hinterlong.kevin.swishticker.QueryEngine;
import com.hinterlong.kevin.swishticker.R;
import com.hinterlong.kevin.swishticker.ui.adapters.TeamItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * A fragment representing a list of Items.
 */
public class TeamsFragment extends Fragment {

    private final FlexibleAdapter<TeamItem> adapter = new FlexibleAdapter<>(null);
    @BindView(R.id.game_list) RecyclerView recyclerView;
    @BindView(R.id.team_fab) FloatingActionButton fab;
    private Unbinder unbinder;

    public TeamsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> startActivity(new Intent(getContext(), NewTeamActivity.class)));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateDataSet(getTeamsList());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        adapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public List<TeamItem> getTeamsList() {
        List<TeamItem> list = new ArrayList<>();
        for (int teamId : QueryEngine.getInstance().getTeamIds()) {
            TeamItem gameItem = new TeamItem(QueryEngine.getInstance().getTeam(teamId), teamId);
            list.add(gameItem);
        }
        return list;
    }
}
