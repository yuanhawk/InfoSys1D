package tech.sutd.pickupgame.util;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomSnapHelper extends LinearSnapHelper {

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            if (!needSnap(manager))
                return null;
        }

        return super.findSnapView(layoutManager);
    }

    private boolean needSnap(LinearLayoutManager manager) {
        return manager.findFirstCompletelyVisibleItemPosition() != 0 && manager.findLastCompletelyVisibleItemPosition() != manager.getItemCount() - 1;
    }
}
