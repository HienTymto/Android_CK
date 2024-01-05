package vn.edu.stu.vuvanhien_dh52001503;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import vn.edu.stu.vuvanhien_dh52001503.Model.HangHoa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String SERVER = "http://document.fitstu.net/wshanghoa/api.php";
    EditText txtMa, txtTen;
    Button btnLuu;
    ArrayList<HangHoa> dsHH;
    ArrayAdapter<HangHoa> adapter;
    ListView lvHH;
    HangHoa chon = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        hienthiDanhsach();
        addEvents();
    }
    private void addControls() {

        txtTen = findViewById(R.id.txtTenSv);
        btnLuu = findViewById(R.id.btnLuu);
        lvHH = findViewById(R.id.lvSv);
        dsHH = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsHH
        );
        lvHH.setAdapter(adapter);
    }

    private void hienthiDanhsach() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> responeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dsHH.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    int len = jsonArray.length();
                    for(int i = 0 ; i<len;i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ma = jsonObject.getInt("ma");
                        String ten = jsonObject.getString("ten");
                        dsHH.add(new HangHoa(ma,ten));
                    }
                    adapter.notifyDataSetChanged();
                }catch (Exception ex){

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
        builder.appendQueryParameter("action","dshh");
        builder.appendQueryParameter("mssv","DH52001503");
        String url = builder.build().toString();
        StringRequest request = new StringRequest(Request.Method.GET,url,responeListener,errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(request);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int ma = 0;
                    String ten = txtTen.getText().toString();
                    HangHoa hh = new HangHoa(ma,ten);
                    xulithem(hh);
                    txtTen.setText("");
                    txtMa.requestFocus();


            }
        });

        lvHH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i>=0&&i<dsHH.size())
                {
                    HangHoa hh = dsHH.get(i);
                    System.out.println(hh);
                    xuliXoa(hh);

                }
                return true;
            }
        });
    }

    private void xuliXoa(HangHoa hh) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> responeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = jsonObject.getBoolean("message");
                    if(result)
                    {
                        Toast.makeText(
                                MainActivity.this,
                                "Xoa nhật thành công",
                                Toast.LENGTH_LONG
                        ).show();
                        hienthiDanhsach();
                    }
                }catch (Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
        builder.appendQueryParameter("action","xoahh");
        builder.appendQueryParameter("mssv","DH52001503");
        builder.appendQueryParameter("ma",hh.getMa()+"");
        String url = builder.build().toString();
        StringRequest request = new StringRequest(Request.Method.GET,url,responeListener,errorListener);
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        requestQueue.add(request);
    }
    private void xulithem(HangHoa hh) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> responeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = jsonObject.getBoolean("message");
                    if(result){
                        Toast.makeText(
                                MainActivity.this,
                                "Thêm thành công",
                                Toast.LENGTH_LONG
                        ).show();
                        hienthiDanhsach();
                    }else
                    {
                        Toast.makeText(MainActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
        builder.appendQueryParameter("action","themhh");
        builder.appendQueryParameter("mssv","DH52001503");
        builder.appendQueryParameter("ten",hh.getTen());
        String url = builder.build().toString();
        StringRequest request = new StringRequest(Request.Method.GET,url,responeListener,errorListener);
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        requestQueue.add(request);
    }
}