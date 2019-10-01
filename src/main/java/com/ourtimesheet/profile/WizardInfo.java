package com.ourtimesheet.profile;

/**
 * Created by Abdus Salam on 10/17/2016.
 */
public class WizardInfo {

  private WizardStatus wizardStatus;

  public WizardInfo(WizardStatus wizardStatus) {
    this.wizardStatus = wizardStatus;
  }

  public WizardStatus getWizardStatus() {
    return wizardStatus;
  }

  public void updateWizardStatus(WizardStatus wizardStatus) {
    this.wizardStatus = wizardStatus;
  }
}
