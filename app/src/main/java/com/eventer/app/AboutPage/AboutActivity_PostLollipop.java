package com.eventer.app.AboutPage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventer.app.R;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.uncod.android.bypass.Bypass;

/**
 * Created by Gulzar on 28-10-2016.
 */
public class AboutActivity_PostLollipop extends AppCompatActivity{
    @BindView(R.id.draggable_frame)
    ElasticDragDismissFrameLayout draggableFrame;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    InkPageIndicator pageIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        pager.setAdapter(new AboutPagerAdapter(this));
        pager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.spacing_normal));
        pageIndicator.setViewPager(pager);

        draggableFrame.addListener(
                new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDragDismissed() {
                        // if we drag dismiss downward then the default reversal of the enter
                        // transition would slide content upward which looks weird. So reverse it.
                        if (draggableFrame.getTranslationY() > 0) {
                            getWindow().setReturnTransition(
                                    TransitionInflater.from(AboutActivity_PostLollipop.this)
                                            .inflateTransition(R.transition.about_return_downward));
                        }
                        finishAfterTransition();
                    }
                });
    }
    static class AboutPagerAdapter extends PagerAdapter {

        private View aboutMagnovitegulzar;
        @Nullable
        @BindView(R.id.about_description)
        TextView about_description;
        private View aboutIcon;
        @Nullable
        //@BindView(R.id.icon_description) TextView iconDescription;
        private View aboutLibs;

        private final LayoutInflater layoutInflater;
        private final Bypass markdown;

        AboutPagerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            markdown = new Bypass(context, new Bypass.Options());
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View layout = getPage(position, collection);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private View getPage(int position, ViewGroup parent) {
            final Resources resources = parent.getResources();
            switch (position) {
                case 0:
                    if (aboutMagnovitegulzar == null) {
                        aboutMagnovitegulzar = layoutInflater.inflate(R.layout.about_eventer, parent, false);
                        ButterKnife.bind(this, aboutMagnovitegulzar);
                    }
                    return aboutMagnovitegulzar;
                case 1:
                    if (aboutIcon == null) {
                        aboutIcon = layoutInflater.inflate(R.layout.about_icon, parent, false);
                        ButterKnife.bind(this, aboutIcon);
                    }
                    return aboutIcon;
//                case 2:
//                    if (aboutLibs == null) {
////                        aboutLibs = layoutInflater.inflate(R.layout.about_magnovite, parent, false);
////                        ButterKnife.bind(this, aboutLibs);
////                        libsList.setAdapter(new LibraryAdapter(parent.getContext()));
//                    }
//                    return aboutLibs;
            }
            throw new InvalidParameterException();
        }
    }

//    private static class LibraryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        private static final int VIEW_TYPE_INTRO = 0;
//        private static final int VIEW_TYPE_LIBRARY = 1;
//        private static final Library[] libs = {
//                new Library("Android support libraries",
//                        "The Android support libraries offer a number of features that are not built into the framework.",
//                        "https://developer.android.com/topic/libraries/support-library",
//                        "https://developer.android.com/images/android_icon_125.png",
//                        false),
//                new Library("ButterKnife",
//                        "Bind Android views and callbacks to fields and methods.",
//                        "http://jakewharton.github.io/butterknife/",
//                        "https://avatars.githubusercontent.com/u/66577",
//                        true),
//                new Library("Bypass",
//                        "Skip the HTML, Bypass takes markdown and renders it directly.",
//                        "https://github.com/Uncodin/bypass",
//                        "https://avatars.githubusercontent.com/u/1072254",
//                        true),
//                new Library("Glide",
//                        "An image loading and caching library for Android focused on smooth scrolling.",
//                        "https://github.com/bumptech/glide",
//                        "https://avatars.githubusercontent.com/u/423539",
//                        false),
//                new Library("JSoup",
//                        "Java HTML Parser, with best of DOM, CSS, and jquery.",
//                        "https://github.com/jhy/jsoup/",
//                        "https://avatars.githubusercontent.com/u/76934",
//                        true),
//                new Library("OkHttp",
//                        "An HTTP & HTTP/2 client for Android and Java applications.",
//                        "http://square.github.io/okhttp/",
//                        "https://avatars.githubusercontent.com/u/82592",
//                        false),
//                new Library("Retrofit",
//                        "A type-safe HTTP client for Android and Java.",
//                        "http://square.github.io/retrofit/",
//                        "https://avatars.githubusercontent.com/u/82592",
//                        false) };
//
//        private final CircleTransform circleCrop;
//
//        LibraryAdapter(Context context) {
//            circleCrop = new CircleTransform(context);
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            switch (viewType) {
//                case VIEW_TYPE_INTRO:
//                    return new LibraryIntroHolder(LayoutInflater.from(parent.getContext())
//                            .inflate(R.layout.about_lib_intro, parent, false));
//                case VIEW_TYPE_LIBRARY:
//                    return createLibraryHolder(parent);
//            }
//            throw new InvalidParameterException();
//        }
//
//        private @NonNull
//        LibraryHolder createLibraryHolder(ViewGroup parent) {
//            final LibraryHolder holder = new LibraryHolder(LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.library, parent, false));
//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = holder.getAdapterPosition();
//                    if (position == RecyclerView.NO_POSITION) return;
//                    holder.itemView.getContext().startActivity(
//                            new Intent(Intent.ACTION_VIEW, Uri.parse(libs[position - 1].link)));
//                }
//            };
//            holder.itemView.setOnClickListener(clickListener);
//            holder.link.setOnClickListener(clickListener);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//            if (getItemViewType(position) == VIEW_TYPE_LIBRARY) {
//                bindLibrary((LibraryHolder) holder, libs[position - 1]); // adjust for intro
//            }
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position == 0 ? VIEW_TYPE_INTRO : VIEW_TYPE_LIBRARY;
//        }
//
//        @Override
//        public int getItemCount() {
//            return libs.length + 1; // + 1 for the static intro view
//        }
//
//        private void bindLibrary(final LibraryHolder holder, final Library lib) {
//            holder.name.setText(lib.name);
//            holder.description.setText(lib.description);
//            DrawableRequestBuilder<String> request = Glide.with(holder.image.getContext())
//                    .load(lib.imageUrl)
//                    .placeholder(R.drawable.avatar_placeholder);
//            if (lib.circleCrop) {
//                request.transform(circleCrop);
//            }
//            request.into(holder.image);
//        }
//    }
//
//    static class LibraryHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.library_image)
//        ImageView image;
//        @BindView(R.id.library_name) TextView name;
//        @BindView(R.id.library_description) TextView description;
//        @BindView(R.id.library_link)
//        Button link;
//
//        LibraryHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    static class LibraryIntroHolder extends RecyclerView.ViewHolder {
//
//        TextView intro;
//
//        LibraryIntroHolder(View itemView) {
//            super(itemView);
//            intro = (TextView) itemView;
//        }
//    }
//
//    /**
//     * Models an open source library we want to credit
//     */
//    private static class Library {
//        final String name;
//        final String link;
//        final String description;
//        final String imageUrl;
//        final boolean circleCrop;
//
//        Library(String name, String description, String link, String imageUrl, boolean circleCrop) {
//            this.name = name;
//            this.description = description;
//            this.link = link;
//            this.imageUrl = imageUrl;
//            this.circleCrop = circleCrop;
//        }
//    }

}
