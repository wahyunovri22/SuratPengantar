package creative.can.com.suratpengantar.Fragment;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import creative.can.com.suratpengantar.Activity.DetailSuratTidakMampuActivity;
import creative.can.com.suratpengantar.ActivityRT.HomeRTctivity;
import creative.can.com.suratpengantar.Adapter.SuratMasukAdapter;
import creative.can.com.suratpengantar.Adapter.SuratWaitingAdapter;
import creative.can.com.suratpengantar.Model.ModelReadSurat;
import creative.can.com.suratpengantar.Model.ResponseModel;
import creative.can.com.suratpengantar.Model.RuleModel;
import creative.can.com.suratpengantar.Model.SuratMenungguDiketahui;
import creative.can.com.suratpengantar.R;
import creative.can.com.suratpengantar.helper.Config;
import creative.can.com.suratpengantar.retrofit.ApiRequest;
import creative.can.com.suratpengantar.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingFragment extends Fragment {

    ProgressDialog pd;
    String  posisiRT,posisiRW;
    LinearLayout div;
    Config config;
    String mStatus;
//    Handler handler = new Handler();
    private List<SuratMenungguDiketahui> list = new ArrayList<>();

    public WaitingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_waiting, container, false);

        div = (LinearLayout) inflaterView.findViewById(R.id.div);

        config = new Config(getActivity());
        posisiRT= config.getSpPosisirt();
        posisiRW = config.getSpPosisirw();
        pd = new ProgressDialog(getActivity());
        getData();

//        this.handler = new Handler();
//        this.handler.postDelayed(runnable, 3000);


        return inflaterView;
    }

    public void getData(){
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();

        ApiRequest apiRequest = RetrofitConfig.getRetrofit().create(ApiRequest.class);
        Call<ResponseModel> get = apiRequest.getSuratWaiting();
        get.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.dismiss();
                list = response.body().getSuratMenungguDiketahui();

                if (list== null){
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
                else {


                    for (final SuratMenungguDiketahui s : list) {
                        if ((s.getPERRT() != null && s.getPERRT().contains(posisiRT)) && (s.getPERRW() != null && s.getPERRW().contains(posisiRW))) {
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View addView = layoutInflater.inflate(R.layout.list_waiting, null);

                            final TextView nik = (TextView) addView.findViewById(R.id.txt_nik);
                            final TextView nama = (TextView) addView.findViewById(R.id.txt_nama);
                            final TextView alamat = (TextView) addView.findViewById(R.id.txt_alamat);
                            final TextView rt = (TextView) addView.findViewById(R.id.txt_rt);
                            final TextView rw = (TextView) addView.findViewById(R.id.txt_rw);
                            final TextView surat = (TextView) addView.findViewById(R.id.txt_surat);
                            final TextView keperluan = (TextView) addView.findViewById(R.id.txt_keperluan);
                            final TextView tanggal = (TextView) addView.findViewById(R.id.txt_tanggal);
                            final TextView status = (TextView) addView.findViewById(R.id.txt_status);
                            final CardView cv = (CardView) addView.findViewById(R.id.cv);

                            nik.setText(s.getNIK());
                            nama.setText(s.getPERNAMAWARGA());
                            alamat.setText(s.getPERALAMAT());
                            rt.setText(s.getPERRT());
                            rw.setText(s.getPERRW());
                            surat.setText(s.getSNAMA());
                            keperluan.setText(s.getPERKEPERLUAN());
                            tanggal.setText(s.getPERCREATEDAT());
                            mStatus = s.getPERSTATUS();
                            if (mStatus.equalsIgnoreCase("REJECT")){
                                status.setText("SURAT ANDA DITOLAK");
                                status.setTextColor(getResources().getColor(R.color.merah));
                            }else if (mStatus.equalsIgnoreCase("WAITING")){
                                status.setText("MENUNGGU PERSETUJUAN");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }
                            else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RT")){
                                status.setText("MENUNGGU PERSETUJUAN RT");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("WAITING_APPROVAL_RW")){
                                status.setText("MENUNGGU PERSETUJUAN RW");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("WAITING_KADES")){
                                status.setText("MENUNGGU KEPALA DESA");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }else if (mStatus.equalsIgnoreCase("PRINT")){
                                status.setText("SURAT ANDA TELAH DICETAK");
                                status.setTextColor(getResources().getColor(R.color.bg_login));
                            }

                            cv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final CharSequence[] dialogItem = {"Detail", "OK", "Ditolak"};

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setTitle("Pilihan");
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, final int i) {
                                            String mNik = s.getPERID();
                                            String mStatus = "WAITING_APPROVAL_RT";
                                            String mStatus2 = "REJECT";
                                            String mRt = config.getSpPosisirt();
                                            String mRw = config.getSpPosisirw();
                                            switch (i) {
                                                case 0:
                                                    Intent intent = new Intent(getActivity(), DetailSuratTidakMampuActivity.class);
                                                    intent.putExtra("nama_surat", s.getSNAMA());
                                                    intent.putExtra("nik", s.getNIK());
                                                    intent.putExtra("no_kk",s.getPERKK());
                                                    intent.putExtra("nama", s.getPERNAMAWARGA());
                                                    intent.putExtra("pekerjaan",s.getPERPEKERJAAN());
                                                    intent.putExtra("tempat_lahir", s.getPERTMPLAHIR());
                                                    intent.putExtra("agama", s.getPERAGAMA());
                                                    intent.putExtra("tgl_lahir", s.getPERTGLLAHIR());
                                                    intent.putExtra("jk", s.getPERJK());
                                                    intent.putExtra("alamat",s.getPERALAMAT());
                                                    intent.putExtra("kelurahan",s.getPERKELURAHAN());
                                                    intent.putExtra("rt",s.getPERRT());
                                                    intent.putExtra("rw",s.getPERRW());
                                                    intent.putExtra("kecamatan",s.getPERKECAMATAN());
                                                    intent.putExtra("kabupaten",s.getPERKABUPATEN());
                                                    intent.putExtra("provinsi",s.getPERPROVINSI());
                                                    intent.putExtra("status_nikah",s.getPERSTATUSNIKAH());
                                                    intent.putExtra("warganegara",s.getPERSTATUSPENDUDUK());
                                                    intent.putExtra("status_kk",s.getPERSTATUSKK());
                                                    intent.putExtra("pendidikan",s.getPERPENDIDIKAN());
                                                    intent.putExtra("keperluan",s.getPERKEPERLUAN());
                                                    intent.putExtra("dibuat",s.getPERCREATEDAT());
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    break;
                                                case 1:
                                                    pd.setMessage("loading");
                                                    pd.setCancelable(false);
                                                    pd.show();
                                                    ApiRequest ap3 = RetrofitConfig.getRetrofit().create(ApiRequest.class);
                                                    Call<ResponseModel> send3 = ap3.postSuratMasuk(mNik, mStatus);
                                                    send3.enqueue(new Callback<ResponseModel>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                                            pd.dismiss();
                                                            Log.d("RETRO", "response : " + response.body().toString());
                                                            String kode = response.body().getKode();
                                                            String pesan = response.body().getPesan();

                                                            if (kode.equals("1")) {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getActivity(), HomeRTctivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                                            pd.dismiss();
                                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            Log.d("RETRO", "Falure : " + t.getMessage());

                                                        }


                                                    });

                                                    break;
                                                case 2:
                                                    pd.setMessage("loading");
                                                    pd.setCancelable(false);
                                                    pd.show();
                                                    ApiRequest api = RetrofitConfig.getRetrofit().create(ApiRequest.class);
                                                    Call<ResponseModel> send = api.rejectSurat(mNik, mStatus2, mRt);
                                                    send.enqueue(new Callback<ResponseModel>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                                            pd.dismiss();
                                                            Log.d("RETRO", "response : " + response.body().toString());
                                                            String kode = response.body().getKode();
                                                            String pesan = response.body().getPesan();

                                                            if (kode.equals("1")) {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getActivity(), HomeRTctivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                                            pd.dismiss();
                                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            Log.d("RETRO", "Falure : " + t.getMessage());

                                                        }
                                                    });
                                                    break;
                                            }
                                        }
                                    });
                                    builder.create().show();
                                }
                            });
                            div.addView(addView);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public final Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            getData();
//            WaitingFragment.this.handler.postDelayed(runnable, 3000);
//        }
//    };

}
