package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.pisces.lau.wishstar.bean.DetailedInfo;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * Created by Liu Wenyue on 2015/8/17.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class DetailedInfoFragment extends Fragment {
    String bookId = "";

    Button button;
    String bookInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailed_info_layout, container, false);
        button = (Button) view.findViewById(R.id.press_info);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到传过来的图书资源ID:
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            bookId = bundle.getString("bookId");
            Log.v("DetailedInfoFragment", bookId);
            bookInfo = "https://api.douban.com/v2/book/" + bookId;
            Log.v("xxx", bookInfo);
            getBookInfo(bookInfo);


        }
    }

    private void getBookInfo(String url) {

        FinalHttp fh = new FinalHttp();
        fh.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoading(long count, long current) {

            }

            @Override
            public void onSuccess(String result) {

                Log.v("kkk", result);
                parseResult(result);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }

    //解析书籍详细信息包括图片和信息
    private void parseResult(String result) {
        Gson gson = new Gson();
        DetailedInfo detailedInfo = gson.fromJson(result, DetailedInfo.class);
        Log.v("gson", detailedInfo.getTitle());
        DetailedInfo.ImagesEntity imagesEntity = detailedInfo.getImages();
        Log.v("gson", imagesEntity.getLarge());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.detailed_share) {
            //点击该图标,会进行分享.社会化分享shareSDK相关

        }
        return super.onOptionsItemSelected(item);

    }
}
