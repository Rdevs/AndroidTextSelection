package rdevs.textselection;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    YATextView tv ;
    TextView tvResult ;
    private ActionMode actionMode;
    ActionMode.Callback callback ;
    int mStartIndex = -1 , mEndINdex = -1 ;
      int[] loc = new int[2] ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (YATextView) findViewById(R.id.tv);
        tvResult = (TextView) findViewById(R.id.tvResult);
        getDelegate().setHandleNativeActionModesEnabled(false);

         callback = new  ActionMode.Callback(){

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

              /*  MenuInflater inflater = mode.getMenuInflater();
                menu.clear();
                inflater.inflate(R.menu.menu, menu);*/
                L("OnCreateActionMode");

                /**
                 *  in sling app code , false is returned. But on devices with 6.0 and above , we will need to return true to show
                 *  the floating popup menu with custom menu items ???
                 */

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                L("onPrepareActionMode");
                // Inflating here so that default options like Select All , Share are not shown in the contextual menu
                // If inflated in onCreateActionMode , default options are shown and we don't want that ??
                MenuInflater inflater = mode.getMenuInflater();
                menu.clear();
                inflater.inflate(R.menu.menu, menu);
                tv.setWindowFocusWait(true);

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                L("onDestroyActionMode");
                tv.setWindowFocusWait(false);
                actionMode = null ;
            }


            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                mode.finish();
                switch (item.getItemId()) {
                    case R.id.m1:
                        tvResult.setText("STRING IS "+tv.getText().toString().substring(mStartIndex,mEndINdex));
                     //   T(tv.getText().toString().substring(mStartIndex,mEndINdex)+"  Start "+mStartIndex+" End "+mEndINdex+" ");
                        L("LOGGING "+tv.getText().toString().substring(mStartIndex,mEndINdex));

                        return true;
                    default:
                        return false;
                }

            }
        } ;


        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

               if (actionMode != null) {
                    return false ;
                }

               if(Build.VERSION.SDK_INT >= 23)
                {
                  actionMode =  startActionMode(callback,ActionMode.TYPE_FLOATING);
               } else {
                   actionMode = startActionMode(callback);
                }

                tv.setCustomSelectionActionModeCallback(callback);

                final TextView textView = (TextView) view;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //This works correct on all devices with Android 6.0
                        int startIndex = textView.getSelectionStart();
                        mStartIndex = startIndex ;
                        int endIndex = textView.getSelectionEnd();
                        mEndINdex = endIndex ;
                        if ((endIndex - startIndex) <= 0) {
                            return;
                        }
                     //   tvResult.setText("START "+tv.getSelectionStart()+" END "+tv.getSelectionEnd()+ tv.getText().toString().substring(startIndex,endIndex));
                    }
                }, 200);
                return false ;
            }

        });


     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 tv.performLongClick();
            }
        },5000);*/


    }

    private void L(String message) {
        Log.e("LOG IT",message);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        tv.getLocationInWindow(loc);
    }

    private void T(String text) {
       Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
