package com.hamara.gumasta.Interfaces;

import android.view.View;

import com.hamara.gumasta.Model.Service;

public interface RecyclerViewStateListener {


      void onCheckedStateChanged(Service service,boolean isChecked);
      void  onClick(View view);

}
