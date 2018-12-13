package fanruiqi.bwie.com.week3_1213;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import fanruiqi.bwie.com.week3_1213.adapter.MyAdapter;
import fanruiqi.bwie.com.week3_1213.bean.ResponseBean;
import fanruiqi.bwie.com.week3_1213.util.OkHttps;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImageView;
    private TextView mTextView;
    private Button mButton;
    private RecyclerView mRecyclerView;
    String urlString="http://www.xieast.com/api/news/news.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initData();
    }

    private void initData() {
        OkHttps.getInstance().doGet(urlString, new OkHttps.OnCallBack() {
            @Override
            public void onFail() {

            }

            @Override
            public void onResponse(String json) {

                ResponseBean responseBean = new Gson().fromJson(json, ResponseBean.class);
                final List<ResponseBean.DataBean> data = responseBean.getData();

                final MyAdapter adapter = new MyAdapter(getApplicationContext(), data);
                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

                adapter.setOnLongClickListener(new MyAdapter.OnLongClickListener() {
                    @Override
                    public void OnLongClick(final int position1) { //长按

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("你确定要删除吗?");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.remove(position1);
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void OnClick(ImageView imageView) {

                        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 3f,1f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 3f,1f);

                        animatorSetsuofang.setDuration(2000);
                        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
                        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
                        animatorSetsuofang.start();

                    }

                });


            }
        });
    }

    private void findViews() {
        mImageView = findViewById(R.id.main_img);
        mTextView = findViewById(R.id.main_text);
        mButton = findViewById(R.id.main_btn);
        mRecyclerView = findViewById(R.id.mian_rv);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn:

                break;
        }
    }
}
