package com.unlogicon.openface.utils;

import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;

/**
 * Created by Nik on 07.05.2016.
 */
public class BboxComparison {

    public static int compare(History history, Detect detect) {
        int pos = 0;
        for (int i = 0; i < detect.getBboxes().size(); i++) {
            if (history.getX1().equals(detect.getBboxes().get(i).getX1()) &&
                    history.getX2().equals(detect.getBboxes().get(i).getX2()) &&
                    history.getY1().equals(detect.getBboxes().get(i).getY1()) &&
                    history.getY2().equals(detect.getBboxes().get(i).getY2())) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
