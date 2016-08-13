package com.etob.android.features.main;

import com.etob.android.data.model.Ribot;
import com.etob.android.features.common.MvpView;
import java.util.List;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
