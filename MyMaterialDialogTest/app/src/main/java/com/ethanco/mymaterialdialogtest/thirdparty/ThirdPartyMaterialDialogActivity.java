package com.ethanco.mymaterialdialogtest.thirdparty;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.ethanco.mymaterialdialogtest.R;

import java.util.ArrayList;

/**
 * 第三方Material Dialog
 */
public class ThirdPartyMaterialDialogActivity extends AppCompatActivity implements View.OnClickListener,ColorChooserDialog.ColorCallback {

    private ArrayList<Object> list;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        initArray();

        findViewById(R.id.btnNormal).setOnClickListener(this);
        findViewById(R.id.btnInput).setOnClickListener(this);
        findViewById(R.id.btnWait).setOnClickListener(this);
        findViewById(R.id.btnSingleChoice).setOnClickListener(this);
        findViewById(R.id.btnMultiChoice).setOnClickListener(this);
        findViewById(R.id.btnPercent).setOnClickListener(this);
        findViewById(R.id.btnCustom).setOnClickListener(this);
        findViewById(R.id.btnSingleList).setOnClickListener(this);
        findViewById(R.id.btnColorChooser).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNormal: //普通
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("Title")
                        .titleColorRes(R.color.colorPrimaryDark)
                        .content("Content")
                        .positiveText("确定")
                        .negativeText("取消")
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(getApplicationContext(), "点击确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(getApplicationContext(), "点击取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                    }
                });
                break;
            case R.id.btnInput: //输入框
                /*new MaterialDialog.Builder(this)
                        .title("请输入")
                        .content("写点什么东西吧")
                        //.inputType(InputType.TYPE_CLASS_TEXT) //默认，可以不加
                        //InputType.TYPE_TEXT_VARIATION_PASSWORD 密码框
                        .input("hint", "预先装载的",false, new MaterialDialog.InputCallback() { //false: allowEmptyInput 输入框为空时是否能点击确定，默认为true
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Toast.makeText(getApplication(), input, Toast.LENGTH_SHORT).show();
                            }
                        }).show();*/

                new MaterialDialog.Builder(this)
                        .title("请输入")
                        .content("写点什么东西吧")
                        //.inputType(InputType.TYPE_CLASS_TEXT) //默认，可以不加
                        //InputType.TYPE_TEXT_VARIATION_PASSWORD 密码框
                        .alwaysCallInputCallback()
                        .input("hint", "预先装载的", false, new MaterialDialog.InputCallback() { //false: allowEmptyInput 输入框为空时是否能点击确定，默认为true
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Toast.makeText(getApplication(), input, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(ThirdPartyMaterialDialogActivity.this, dialog.getInputEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.btnWait: //等待对话框
                LoadingDialog.show(this);
                break;
            case R.id.btnSingleChoice: //单选
                new MaterialDialog.Builder(this)
                        .title("单选对话框")
                        .items(list)
                        /* 没有圆点 radiobutton
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                            }
                        })*/
                        //有圆点 radiobutton
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Toast.makeText(getApplication(), "which-" + which + ":" + text, Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })
                        .alwaysCallSingleChoiceCallback()//设置该选项每次点击都会触发itemsCallbackSingleChoice
                        .positiveText("确定")
                        .show();
                break;
            case R.id.btnMultiChoice: //多选
                Integer[] defChoices = new Integer[2];
                defChoices[0] = 1;
                defChoices[1] = 2;
                new MaterialDialog.Builder(this)
                        .title("自定义")
                        .items(list)
                        .itemsCallbackMultiChoice(defChoices, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                Toast.makeText(getApplication(), "选择了" + which.length + "项", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })//.alwaysCallMultiChoiceCallback()//设置该选项每次点击都会触发itemsCallbackMultiChoice
                        .positiveText("确定")
                        .show();
                break;
            case R.id.btnPercent:
                progress = 0;
                final MaterialDialog progressDialog = new MaterialDialog.Builder(this)
                        .title("进度条对话框")
                        .progress(false, 100) //参数三:是否显示最大最小值 0/100
                        .show();

                new Thread() {
                    @Override
                    public void run() {

                        while (progressDialog.getCurrentProgress() != progressDialog.getMaxProgress()) {
                            if (progressDialog.isCancelled()) break;
                            SystemClock.sleep(50);
                            progressDialog.incrementProgress(1); //值+1
                        }

                        progressDialog.dismiss();
                    }
                }.start();
                break;
            case R.id.btnCustom:
                boolean wrapInScrollView = true;
                new MaterialDialog.Builder(this)
                        .title("自定义对话框")
                        .customView(R.layout.custom_view, wrapInScrollView)
                        .positiveText("确定")
                        .show();
                break;
            case R.id.btnSingleList:
                //列表 竖直
                /*final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
                    @Override
                    public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                        Toast.makeText(getApplicationContext(), "choice:"+index, Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .content("username@gmail.com")
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .content("user02@gmail.com")
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .content(R.string.app_name)
                        .icon(R.mipmap.ic_launcher)
                        .iconPaddingDp(8)
                        .build());

                new MaterialDialog.Builder(this)
                        .title(R.string.set_backup)
                        .show();*/

                final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
                    @Override
                    public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                        Toast.makeText(getApplicationContext(), "choice:"+index, Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());

                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());

                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());
                adapter.add(new MaterialSimpleListItem.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .backgroundColor(Color.WHITE)
                        .build());

                new MaterialDialog.Builder(this)
                        .title("请选择图标")
                        .adapter(adapter, new GridLayoutManager(this,3))
                        .show();
                break;
            case R.id.btnColorChooser:
                boolean accent = true;
                int accentPreselect = getResources().getColor(R.color.colorAccent);
                int primaryPreselect = getResources().getColor(R.color.colorPrimary);
                // Pass a context, along with the title of the dialog
                new ColorChooserDialog.Builder(this, R.string.choice_color_first)
                        .titleSub(R.string.choice_color_second)  // title of dialog when viewing shades of a color
                        .accentMode(accent)  // when true, will display accent palette instead of primary palette
                        .doneButton(R.string.md_done_label)  // changes label of the done button
                        .cancelButton(R.string.md_cancel_label)  // changes label of the cancel button
                        .backButton(R.string.md_back_label)  // changes label of the back button
                        .preselect(accent ? accentPreselect : primaryPreselect)  // optionally preselects a color
                        .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
                        .show(this); // an AppCompatActivity which implements ColorCallback
                break;
            default:
        }
    }

    private void initArray() {
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add("Item" + i);
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        Toast.makeText(ThirdPartyMaterialDialogActivity.this, "颜色选择:"+selectedColor, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        Toast.makeText(ThirdPartyMaterialDialogActivity.this, "颜色选择 dismiss", Toast.LENGTH_SHORT).show();
    }
}
