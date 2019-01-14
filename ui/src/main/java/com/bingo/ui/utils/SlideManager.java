package com.bingo.ui.utils;

import com.bingo.ui.SlideLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for Slide List
 */
public class SlideManager {
    private List<SlideLayout> slides;

    public SlideManager() {
        slides = new ArrayList<>();
    }

    public void onChange(SlideLayout layout, boolean isOpen) {
        if (isOpen) {
            slides.add(layout);
        } else {
            slides.remove(layout);
        }
    }

    public boolean closeAll(SlideLayout layout) {
        boolean ret = false;
        if (slides.size() <= 0) {
            return false;
        }
        for (int i = 0; i < slides.size(); i++) {
            SlideLayout slide = slides.get(i);
            if (slide != null && slide != layout) {
                slide.close();
                slides.remove(slide); // Unnecessary
                ret = true;
                i--;
            }
        }
        return ret;
    }
}
