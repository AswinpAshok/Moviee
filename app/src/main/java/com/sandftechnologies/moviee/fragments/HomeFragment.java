package com.sandftechnologies.moviee.fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandftechnologies.moviee.Constants;
import com.sandftechnologies.moviee.R;
import com.sandftechnologies.moviee.api.clients.MovieClient;
import com.sandftechnologies.moviee.bottom_sheets.MovieSheet;
import com.sandftechnologies.moviee.listeners.MovieClickListener;
import com.sandftechnologies.moviee.models.NowPlayingMovies;
import com.sandftechnologies.moviee.recyclerview_components.NowPlayingRecyclerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Constants,MovieClickListener {

    private RecyclerView now_playing_recycler;
    private LinearLayoutManager layoutManager;
    private NowPlayingRecyclerAdapter adapter;

    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;



    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Moviee");

        now_playing_recycler=rootView.findViewById(R.id.now_playing_recycler);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        now_playing_recycler.setLayoutManager(layoutManager);

        now_playing_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();


                if (!isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        loadMoreItems();
                    }
                }
            }
        });

        getNowPlaying(currentPage);

        return rootView;
    }

    protected void loadMoreItems() {
        currentPage += 1; //Increment page index to load the next one
        getNowPlaying(currentPage);
    }



    public boolean isLastPage() {
        return isLastPage;
    }



    private void getNowPlaying(final int page) {
        Retrofit.Builder builder=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);
        Retrofit retrofit= builder.build();
        MovieClient movieClient=retrofit.create(MovieClient.class);
        Call<NowPlayingMovies> call=movieClient.getNowPlaying(MY_KEY,"en-US",page);
        call.enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies> call, Response<NowPlayingMovies> response) {
                if(page==1) {
                    adapter = new NowPlayingRecyclerAdapter(response.body().getMovies());
                    adapter.setMovieClickListener(HomeFragment.this);
                    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

                    TOTAL_PAGES=response.body().getTotalPages();

                SnapHelper snapHelper=new LinearSnapHelper();
                snapHelper.attachToRecyclerView(now_playing_recycler);

                    now_playing_recycler.setItemAnimator(itemAnimator);
                    now_playing_recycler.setAdapter(adapter);
                }else {
                    try {

                        adapter.addMore(response.body().getMovies());
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    if(currentPage==TOTAL_PAGES){
                        isLastPage=true;
                    }
                }

            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {
                Log.d("###", "onFailure: "+t);

            }
        });
    }

    @Override
    public void OnMovieClicked(String title, String overview, String posterPath) {
        MovieSheet sheet=new MovieSheet();
        sheet.setData(posterPath,title,overview);
        BottomSheetDialogFragment bottomSheetDialogFragment = sheet;
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }
}
