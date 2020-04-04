// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package walkthrough.toolWindow.tutorial;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class TutorialViewManager {

  private JButton back;
  private JButton nextButton;
  private JPanel myToolWindowContent;
  private JLabel stepHeadline;
  private JLabel tutType;
  private JTextPane stepContent;
  private JProgressBar tutorialProgress;

  public TutorialViewManager(ToolWindow toolWindow) {
    tutType.setText("Einstieg in IntelliJ - Tutorial");
    back.addActionListener(e -> toolWindow.hide(null));
    nextButton.addActionListener(e -> currentDateTime());
    stepHeadline.setText("Klick auf Loslegen");
    tutorialProgress.setString("1/16");
  }

  public void currentDateTime() {
    stepHeadline.setText("Good Morning");
    stepContent.setText("It is recommended that as ensuring that JavaFX Runtime is available.");
    tutorialProgress.setString("2/16");
    tutorialProgress.setValue(10);
  }

  public JPanel getContent() {
    return myToolWindowContent;
  }
}
