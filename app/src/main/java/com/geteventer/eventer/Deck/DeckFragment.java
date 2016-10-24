package com.geteventer.eventer.Deck;

/**
 * Created by Gulzar on 24-10-2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geteventer.eventer.Event.EventActivity;
import com.geteventer.eventer.R;
import com.geteventer.eventer.api.EventerApi;
import com.geteventer.eventer.common.Bindable;
import com.geteventer.eventer.model.Event;

import com.geteventer.eventer.util.ViewUtils;
import com.wenchao.cardstack.CardStack;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Gulzar on 18-10-2016.
 */
public class DeckFragment extends Fragment implements Bindable<List<Event>> {
    @BindView(R.id.card_stack) CardStack mCardStack;
    @BindView(R.id.progress_views) View mProgressView;
    @BindView(R.id.conection_error_container) View mErrorContainer;

    private DeckAdapter mAdapter;
    private Unbinder mUnbinder;
    private int mCurrentPosition = 0;
    private int size=0;


    public DeckFragment(){}
    public static Fragment newInstance() {
        return new DeckFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private DeckListener mDeckListener = new DeckListener() {
        @Override void onCardSwiped(int direction, int swipedIndex) {
            mCurrentPosition++;
            //Loop Cards
            if(mCurrentPosition%size==0) {
                loadNext(0);
            }

        }

        @Override public void topCardTapped() {
            Event event = mAdapter.getItem(mCurrentPosition);
            Intent i=new Intent(getContext(),EventActivity.class);
            Bundle b=new Bundle();
            b.putParcelable("EXTRA_EVENT", Parcels.wrap(event));
            i.putExtras(b);
            startActivity(i);
        }
    };



    private void loadNext(int i) {
        mProgressView.setVisibility(View.VISIBLE);
        (new EventerApi()).APIcall(new Bindable<List<Event>>() {
            @Override
            public void bind(List<Event> events) {
                size=events.size();
                loadToBind(events);
            }});}

    private void loadToBind(List<Event> events)
    {
        Collections.shuffle(events);
        bind(events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_deck, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        loadNext(0);
        setupPadding();
        return view;
    }

    @OnClick(R.id.textview_retry) public void onRetryClicked() {
        mProgressView.setVisibility(View.VISIBLE);
        ViewUtils.fadeView(mErrorContainer, false, 250);
    }


    @Override
    public void bind(List<Event> events) {
        ViewUtils.fadeView(mCardStack, true, 250);
        mProgressView.setVisibility(View.GONE);
        if (mAdapter == null) {
            mAdapter = new DeckAdapter(getContext(), events);
             mCardStack.setListener(mDeckListener);
            mCardStack.setAdapter(mAdapter);
        } else {
            mAdapter.addAll(events);
        }
    }

    private void setupPadding() {
        int navigationBarHeight = ViewUtils.getNavigationBarHeight();
        mCardStack.setPadding(ViewUtils.dpToPx(14), ViewUtils.dpToPx(52),
                ViewUtils.dpToPx(14), 0 );
    }
    private void handleError() {
        mProgressView.setVisibility(View.GONE);
        ViewUtils.fadeView(mCardStack, false, 250);
        ViewUtils.fadeView(mErrorContainer, true, 250);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        Log.d("XOXO","OnDestroy Called");
    }


}

