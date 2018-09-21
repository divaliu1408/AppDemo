package com.diva.appdemo.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diva.appdemo.Adapter.MyAdapter;
import com.diva.appdemo.Manager.BaseActivity;
import com.diva.appdemo.R;
import com.diva.appdemo.Utils.CustomPopWindow;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_popup_window)
public class PopupWindowActivity extends BaseActivity {
private Context context;
    private CustomPopWindow mCustomPopWindow;
    private CustomPopWindow mListPopWindow;
    private CustomPopWindow mPopWindow;
    @ViewInject(R.id.button1)
    TextView mButton1;
    @ViewInject(R.id.button2)
    TextView mButton2;
    @ViewInject(R.id.button3)
    TextView mButton3;
    @ViewInject(R.id.button4)
    TextView mButton4;
    @ViewInject(R.id.button5)
    TextView mButton5;
    @ViewInject(R.id.button6)
    TextView mButton6;
    @ViewInject(R.id.button7)
    TextView mButton7;
    @ViewInject(R.id.button8)
    Button mButton8;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_popupwindow);
        x.view().inject(this);
        context=this;
    }

    @Event(R.id.button1)
    private void showPopBottom(View view){
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout1)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create();
        popWindow.showAsDropDown(mButton1,0,10);
    }

    @Event(R.id.button2)
    private void showPopTop(View view){
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout2)
                .create();
        popWindow.showAsDropDown(mButton2,0,-(mButton2.getHeight()+popWindow.getHeight()));

    }

    @Event(R.id.button3)
    private void showPopMenu(View view){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
        handleLogic(contentView);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create()
                .showAsDropDown(mButton3,0,20);
    }

    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        break;
                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        break;
                    case R.id.menu3:
                        showContent = "点击 Item菜单3";
                        break;
                    case R.id.menu4:
                        showContent = "点击 Item菜单4";
                        break;
                    case R.id.menu5:
                        showContent = "点击 Item菜单5" ;
                        break;
                }
                Toast.makeText(PopupWindowActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
    }

    @Event(R.id.button4)
    private void showPopListView(View view){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
        handleListView(contentView);
        mListPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .create()
                .showAsDropDown(mButton4,0,20);

    }

    private void handleListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private List<String> mockData(){
        List<String> data = new ArrayList<>();
        for (int i=0;i<100;i++){
            data.add("Item:"+i);
        }

        return data;
    }
    @Event(R.id.button5)
    private void showPopTopWithDarkBg(View view){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);
        handleLogic(contentView);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.d("popWindow", "onDismiss");
                    }
                })
                .create()
                .showAsDropDown(mButton5,0,20);
    }

    @Event(R.id.button6)
    private void useInAndOutAnim(View view){
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout1)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .create()
                .showAsDropDown(mButton6,0,10);
    }

    @Event(R.id.button7)
    private void touchOutsideNotDismiss(View view){
     View contentView =LayoutInflater.from(this).inflate(R.layout.pop_layout_close,null);
     View.OnClickListener listener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Log.e("button7", "onClick: " );
             mPopWindow.dissmiss();
         }
     };
     contentView.findViewById(R.id.close_pop).setOnClickListener(listener);
     mPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
             .setView(contentView)
             .enableOutsideTouchableDissmiss(false)
             .create();
     mPopWindow.showAsDropDown(mButton7,0,10);
    }


    /**
     *popupWindow弹出小键盘
     * @param view
     */
    @Event(R.id.button8)
    private void xueyangInput(View view){
        LayoutInflater inflater = LayoutInflater.from(context);
        GridLayout layout = (GridLayout) inflater.inflate(R.layout.layout_xueyang_input, null);
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(layout)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create();

        popWindow.showAsDropDown(mButton8,-150,10);
        View.OnClickListener listener = new View.OnClickListener() {
            Button editButton=mButton8;
            @Override
            public void onClick(View view) {
                String old = editButton.getText().toString();
                String num = ((Button) view).getText().toString();
                if (num.isEmpty()) {
                    if (editButton.getText().length() > 0) {
                        editButton.setText(old.substring(0, old.length() - 1));
                    }
                } else {
                    editButton.setText(old.concat(num));
                }
//                mButton8.setText(((Button)view).getText().toString());
//                Log.d("onClick", "onClick: "+((Button)view).getText().toString());
            }
        };

        for (Integer i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof Button) {
                v.setOnClickListener(listener);
            }
        }

    }


}
