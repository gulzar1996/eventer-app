package com.eventer.app.RecyclerEvent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by gaurav on 27/10/16.
 */
public class MyEvents extends MyEventListFragment implements ObservableScrollViewCallbacks {
    public static Fragment newInstance() {
        return new MyEvents();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setScrollViewCallbacks(this);
//        mbbar = getActivity().findViewById(R.id.abottom_bar);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("events");
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        if (scrollState == ScrollState.UP)
//            if (scrollState == ScrollState.UP) {
//                    mbbar.setVisibility(View.VISIBLE);
//                    mbbar.setAlpha(0.0f);
//                    mbbar.animate().translationY(mbbar.getHeight()).alpha(1.0f)
//                            .setListener(new Animator.AnimatorListener() {
//                                @Override
//                                public void onAnimationStart(Animator animator) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animator animator) {
//                                    mbbar.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onAnimationCancel(Animator animator) {
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animator animator) {
//
//                                }
//                            });
//
//            } else if (scrollState == ScrollState.DOWN) {
//                mbbar.setVisibility(View.INVISIBLE);
//                mbbar.setAlpha(0.0f);
//                mbbar.animate().translationY(0).alpha(1.0f)
//                        .setListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animator) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animator) {
//                                mbbar.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animator) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animator) {
//
//                            }
//                        });
//
//            }
    }
}

