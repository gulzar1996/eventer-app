package com.geteventer.eventer.Deck;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.geteventer.eventer.model.Event;
import com.geteventer.eventer.ui.EventCardView;

import java.util.List;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class DeckAdapter extends ArrayAdapter<Event> {

    public DeckAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    private static class ViewHolder {
        EventCardView view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Event event = getItem(position);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = new EventCardView(getContext());
            viewHolder.view = (EventCardView) view;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.view.bind(event);
        return view;
    }
}
