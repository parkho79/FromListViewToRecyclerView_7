package com.parkho.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhMainActivity extends AppCompatActivity {

    // List item
    private List<PhCountryItem> mItemList;

    // List view
    private ListView mListView;

    // ListView adapter
    private PhCountryArrayAdapter mCountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List 설정
        bindList();

        // 삽입 설정
        bindInsert();

        // 수정 설정
        bindModify();

        // 삭제 설정
        bindDelete();
    }

    /**
     * List 설정
     */
    private void bindList() {
        mItemList = new ArrayList<>();
        mItemList.add(new PhCountryItem(R.drawable.ico_southkorea, "Korea"));
        mItemList.add(new PhCountryItem(R.drawable.ico_china, "China"));
        mItemList.add(new PhCountryItem(R.drawable.ico_japan, "Japan"));
        mItemList.add(new PhCountryItem(R.drawable.ico_unitedstates, "America"));
        mItemList.add(new PhCountryItem(R.drawable.ico_newzealand, "NewZealand"));

        mListView = (ListView) findViewById(R.id.listview);

        // Header 추가
        final View header = getLayoutInflater().inflate(R.layout.content_list_header, null, false) ;
        mListView.addHeaderView(header);

        // Footer 추가
        final View footer = getLayoutInflater().inflate(R.layout.content_list_footer, null, false) ;
        mListView.addFooterView(footer);

        // Adapter 추가
        mCountryAdapter = new PhCountryArrayAdapter(this, mItemList);
        mListView.setAdapter(mCountryAdapter);

        // List item click event 처리
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final PhCountryItem item = (PhCountryItem) a_parent.getItemAtPosition(a_position);
                if (item == null) {
                    // 여기로 오면 header 나 footer 다.
                    if (a_position == 0) {
                        Toast.makeText(PhMainActivity.this, "The header is selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PhMainActivity.this, "The footer is selected", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                Toast.makeText(PhMainActivity.this, item.getCountry() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 삽입 설정
     */
    private void bindInsert() {
        findViewById(R.id.btn_insert).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Item 추가
                mItemList.add(new PhCountryItem(R.drawable.ico_southkorea, "Korea " + mItemList.size()));

                // List 반영
                mCountryAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 수정 설정
     */
    private void bindModify() {
        findViewById(R.id.btn_modify).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = mListView.getCheckedItemPosition() - mListView.getHeaderViewsCount();;
                if (position == -1 || position == mItemList.size()) {
                    Toast.makeText(PhMainActivity.this, R.string.err_no_selected_item, Toast.LENGTH_SHORT).show();
                    return;
                }

                // List item 수정
                PhCountryItem countryItem = mItemList.get(position);
                countryItem.setCountry(countryItem.getCountry() + " is modified");

                // List 반영
                mCountryAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 삭제 설정
     */
    private void bindDelete() {
        findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = mListView.getCheckedItemPosition() - mListView.getHeaderViewsCount();
                if (position == -1 || position == mItemList.size()) {
                    Toast.makeText(PhMainActivity.this, R.string.err_no_selected_item, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 선택한 list item 삭제
                mItemList.remove(position);

                // 선택 항목 초기화
                mListView.setItemChecked(-1, true);

                // List 반영
                mCountryAdapter.notifyDataSetChanged();
            }
        });
    }
}