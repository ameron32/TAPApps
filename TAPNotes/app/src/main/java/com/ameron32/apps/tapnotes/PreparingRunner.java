package com.ameron32.apps.tapnotes;

/**
 * ABSTRACTION
 */
public class PreparingRunner {

  private final PreparingRunnable mRunnable;

  public static abstract class PreparingRunnable {
    public abstract void onRunComplete(boolean runFinishedSuccessfully);
    public abstract boolean hasRun();

    public boolean onRunPrepare() { return true; }
    public abstract boolean runWithResult();
  }

  public PreparingRunner(PreparingRunnable runnable) {
    mRunnable = runnable;
  }

  public void run() {
    if (mRunnable == null) {
      return;
    }

    if (hasRun(mRunnable)) {
      return;
    }

    boolean prepared = onRunPrepare(mRunnable);
    if (!prepared) {
      return;
    }

    boolean successful = runRunnable(mRunnable);

    onRunComplete(mRunnable, successful);
  }

  protected boolean hasRun(PreparingRunnable runnable) {
    return runnable.hasRun();
  }

  protected boolean onRunPrepare(PreparingRunnable runnable) {
    return runnable.onRunPrepare();
  }

  protected boolean runRunnable(PreparingRunnable runnable) {
    return runnable.runWithResult();
  }

  protected void onRunComplete(PreparingRunnable runnable, boolean successful) {
    runnable.onRunComplete(successful);
  }
}
