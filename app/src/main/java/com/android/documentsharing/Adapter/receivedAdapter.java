package com.android.documentsharing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.documentsharing.Holder.documentHolder;
import com.android.documentsharing.IconsHolder;
import com.android.documentsharing.R;
import com.android.documentsharing.UpdateOnlineStatus;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class receivedAdapter extends RecyclerView.Adapter {
    private ArrayList<documentHolder> arrayList;
    private Context context;
    private static final int empty=0;
    private static final int not_empty=1;
    private int count;
    private String dname,dsize,dtime,ddate,dreceiver;
    boolean daccess;
    public receivedAdapter(Context context,ArrayList<documentHolder> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==empty){
            return new Empty(LayoutInflater.from(context).inflate(R.layout.empty_document,parent,false));
        }else {
            return new NotEmpty(LayoutInflater.from(context).inflate(R.layout.shared_document_item,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass()== NotEmpty.class){
            NotEmpty container=(NotEmpty) holder;
            dname=arrayList.get(position).getName();
            dsize=arrayList.get(position).getSize();
            dtime=arrayList.get(position).getTime();
            ddate=arrayList.get(position).getDate();
            dreceiver=arrayList.get(position).getReceiverName();
            daccess=arrayList.get(position).isAccess();
            container.name.setSelected(true);
            container.property.setSelected(true);
            container.receiver.setSelected(true);
            container.name.setText(dname);
            container.property.setText(String.format("%s | %s | %s",dsize,dtime,ddate));
            container.receiver.setText(String.format("Received from %s",dreceiver));
            container.icon.setImageResource(IconsHolder.getIcon(arrayList.get(position).getExtension()));
            container.option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Date = "+ UpdateOnlineStatus.getCurrentDate(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if (holder.getClass() == Empty.class){
            Empty hold=(Empty) holder;
            hold.info.setText("Nothing is received ! please check internet connection or pull down to refresh list!");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (count == -1){
            return empty;
        }
        else{
            return not_empty;
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() == 0){
            count=-1;
            return count+2;
        }else {
            count=arrayList.size();
            return count;
        }
    }
    public class Empty extends RecyclerView.ViewHolder{
        TextView info;
        public Empty(@NonNull View itemView) {
            super(itemView);
            info=itemView.findViewById(R.id.document_info);
        }

    }
    public class NotEmpty extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView name,property,receiver;
        ImageView option;
        public NotEmpty(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.document_icon);
            property=itemView.findViewById(R.id.document_property);
            receiver=itemView.findViewById(R.id.document_receiver);
            option=itemView.findViewById(R.id.document_option);
            name=itemView.findViewById(R.id.document_name);
        }
    }
}
