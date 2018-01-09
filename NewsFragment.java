package creative.can.com.suratpengantar.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import creative.can.com.suratpengantar.Adapter.NewsAdapter;
import creative.can.com.suratpengantar.Model.DataModel;
import creative.can.com.suratpengantar.Model.ResponseModel;
import creative.can.com.suratpengantar.R;
import creative.can.com.suratpengantar.retrofit.ApiRequest;
import creative.can.com.suratpengantar.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private CardView cardNews;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    //DataModel dataModel;
    private List<DataModel> list = new ArrayList<>();
    LinearLayout layoutError;
    Button btnReload;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_news, container, false);
        cardNews = (CardView) inflaterView.findViewById(R.id.card_news);
        layoutError = (LinearLayout)inflaterView.findViewById(R.id.ly_error);
        btnReload = (Button)inflaterView.findViewById(R.id.btn_reload);

        pd = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView)inflaterView.findViewById(R.id.rv_news);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Reload();

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutError.setVisibility(View.GONE);
                Reload();
            }
        });

        return inflaterView;
    }

    public void Reload(){
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();

        ApiRequest apiRequest = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get = apiRequest.getApiNews();
        get.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                list = response.body().getNews();
                mAdapter = new NewsAdapter(getActivity(), list);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                layoutError.setVisibility(View.VISIBLE);
            }
        });
    }


}
