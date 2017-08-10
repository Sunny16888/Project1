package com.anyfish.app.chat.face.fishcanon;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.anyfish.app.R;
import cn.anyfish.nemo.util.widget.draggridview.DragGridView;
import cn.anyfish.nemo.util.widget.draggridview.DragGridView.ILongClikView;

import com.anyfish.app.widgets.AnyfishActivity;
import com.anyfish.app.widgets.face.bean.LableBean;

/**
 * @Description: 我的常用鱼典项--长按移动位置
 * @package: com.anyfish.app.chat.fishcanon
 * @author linms
 * @date 2015-08-14
 */
public class FishCanonActivity extends AnyfishActivity implements ILongClikView
{
    private DragGridView mDragGridView;
    private FishCanonDragAdapter mAdapter;
    private ArrayList<fishCanonItem> mIconList;
    private LableBean mLableBean;
    private Boolean mIsUsing = true;// 默认是在正在使用画面
    private int [] mLongPosList;// 用于记录长按的位置,用于显示小点

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_fish_canon);
        initData();
        initView();
    }

    private void initData()
    {
        //mLableBean = (LableBean) getIntent().getSerializableExtra("labelbean");
        //mIconList = (ArrayList<fishCanonItem>) new DataLoad(this).getPageDataList(mLableBean);
        if(mIconList ==null)
        {
            mIconList = new ArrayList<fishCanonItem>();
        }
        testData();
        mLongPosList = new int[1];
        mLongPosList[0] = -1;
    }

    private void initView()
    {
        ((TextView) findViewById(R.id.chat_actionbar_title_tv)).setText(getString(R.string.chat_my_fishcanon));
        ((ImageView) findViewById(R.id.chat_actionbar_right_iv)).setVisibility(View.GONE);
        findViewById(R.id.chat_actionbar_right_iv).setOnClickListener(this);
        findViewById(R.id.chat_actionbar_back_iv).setOnClickListener(this);
        mDragGridView = (DragGridView)findViewById(R.id.chat_fishcanon_dg);
        mDragGridView.setIsSwapItem(false);
        mAdapter = new FishCanonDragAdapter(this, mIconList);
        mDragGridView.setAdapter(mAdapter);
        mDragGridView.setLongClickViewListener(this);
        mDragGridView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(mApplication, ((fishCanonItem) mAdapter.getItem(position)).faceName, Toast.LENGTH_SHORT).show();
                if (mIsUsing)
                {
                    //从正在使用,移除到未使用
                }
                else
                {
                    //从未使用,添加到正在使用
                }
                mAdapter.setOnLongClickItemPos(-1);
                mAdapter.notifyDataSetChanged();
                
            }
        });
        findViewById(R.id.chat_fish_canon_use_llyt).setVisibility(View.VISIBLE);
        findViewById(R.id.chat_fish_canon_no_use_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_tip_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fishcanon_use_tv).setOnClickListener(this);
        findViewById(R.id.chat_fishcanon_no_use_tv).setOnClickListener(this);
        findViewById(R.id.chat_fishcanon_edit_tv).setOnClickListener(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.chat_actionbar_right_iv:
/*            Intent intent = new Intent(FishCanonActivity.this, FishCanonDeleteActivity.class);
            //intent.putExtra("data", mIconList);
            intent.putExtra("labelbean", mLableBean);
            startActivityForResult(intent, 0);*/
            doConfirm();
            break;
        case R.id.chat_actionbar_back_iv:// 返回
            back();
            break;
        case R.id.chat_fishcanon_edit_tv:// 编辑
            edit();
            break;            
        case R.id.chat_fishcanon_use_tv:// 正在使用的鱼典
            showUsedFishCanon();
            break;
        case R.id.chat_fishcanon_no_use_tv:// 未使用的鱼典
            showNoUsedFishCanon();
            break;
        default:
            break;
        }

    }
    /** 未使用的鱼典 **/
    private void showNoUsedFishCanon()
    {
        findViewById(R.id.chat_fish_canon_no_use_llyt).setVisibility(View.VISIBLE);
        findViewById(R.id.chat_fish_canon_use_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_tip_llyt).setVisibility(View.GONE);
        mDragGridView.setIsSwapItem(false);
        mIsUsing = false;
        if (mAdapter != null)
        {
            mAdapter.setPointType(false);
        }
        
    }
    /** 正在使用的鱼典 **/
    private void showUsedFishCanon()
    {
        findViewById(R.id.chat_fish_canon_no_use_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_use_llyt).setVisibility(View.VISIBLE);
        findViewById(R.id.chat_fish_canon_tip_llyt).setVisibility(View.GONE);
        mDragGridView.setIsSwapItem(false);
        mIsUsing = true;
        mAdapter.setPointType(true);
    }

    /** 编辑 **/
    private void edit()
    {
        findViewById(R.id.chat_actionbar_right_iv).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.chat_actionbar_right_iv)).setImageResource(R.drawable.ic_chat_white_confirm);
        findViewById(R.id.chat_fishcanon_edit_tv).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_no_use_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_use_llyt).setVisibility(View.GONE);
        findViewById(R.id.chat_fish_canon_tip_llyt).setVisibility(View.VISIBLE);
        mDragGridView.setIsSwapItem(true);
        if (mIsUsing)
        {
            ((TextView) findViewById(R.id.chat_actionbar_title_tv)).setText(getString(R.string.chat_fishcanon_use));
        }
        else
        {
            ((TextView) findViewById(R.id.chat_actionbar_title_tv)).setText(getString(R.string.chat_fishcanon_no_use));
        }
    }
    /** 确定 **/
    private void doConfirm()
    {
        if (mIsUsing)
        {
            showUsedFishCanon();
        }
        else
        {
            showNoUsedFishCanon();
        }
        ((TextView) findViewById(R.id.chat_actionbar_title_tv)).setText(getString(R.string.chat_my_fishcanon));
        ((ImageView) findViewById(R.id.chat_actionbar_right_iv)).setVisibility(View.GONE);
        findViewById(R.id.chat_fishcanon_edit_tv).setVisibility(View.GONE);        
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
/*        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == 0) && (resultCode == RESULT_OK))
        {//从鱼典删除画面返回
            mIconList = (ArrayList<fishCanonItem>) data.getSerializableExtra("data");
            if(mIconList ==null)
            {
                mIconList = new ArrayList<fishCanonItem>();
                mAdapter.notifyDataSetChanged();
            }            
        } */                
    }
    
    private void back()
    {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
    /** 长按显示小红点  **/
    @Override
    public boolean onLongClickView(View view,int pos)
    {
        if (mIsUsing)
        {
            ((ImageView) view.findViewById(R.id.chat_fish_canon_point_iv)).setImageResource(R.drawable.ic_chat_fish_canon_del);
        }
        else
        {
            ((ImageView) view.findViewById(R.id.chat_fish_canon_point_iv)).setImageResource(R.drawable.ic_chat_fish_canon_add);
        }
        view.findViewById(R.id.chat_fish_canon_point_iv).setVisibility(View.VISIBLE);
        if (mAdapter!=null)
        {
            mAdapter.setOnLongClickItemPos(pos);
        }
        return true;
    }
    /** 测试用的数据 **/
    class fishCanonItem
    {
        int drawable;
    }
    private void testData()
    {
        int[] pathStr = new int[12];
        pathStr[0] = R.drawable.yuyou_bg_picture_taste2;
        pathStr[1] = R.drawable.yuyou_bg_picture_taste3;
        pathStr[2] = R.drawable.yuyou_bg_picture_taste4;
        pathStr[3] = R.drawable.yuyou_bg_picture_taste5;
        pathStr[4] = R.drawable.yuyou_bg_picture_taste6;
        pathStr[5] = R.drawable.yuyou_bg_picture_taste7;
        pathStr[6] = R.drawable.yuyou_bg_picture_taste8;
        pathStr[7] = R.drawable.yuyou_bg_picture_taste9;
        pathStr[8] = R.drawable.yuyou_bg_picture_taste10;        
        pathStr[9] = R.drawable.ic_routes_origin;
        pathStr[10] = R.drawable.ic_routes_self_normal;
        pathStr[11] = R.drawable.ic_routes_fire_press;
        for(int i = 0; i < 12; i++)
        {
            fishCanonItem item = new fishCanonItem();
            item.drawable = pathStr[i];
            mIconList.add(item);
        }
    }
    // 在线修改--增加注释
    // 通过TortoiseGit进行修改
    // 在线修改后，通过TortoiseGit本地检出
}
