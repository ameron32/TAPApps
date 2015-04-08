package com.ameron32.apps.tapnotes.di.stabbed.mport;

import dagger.ObjectGraph;

public interface StabbedContext {
    /**
     * Inject the supplied {@code object} using the contexts graph.
     */
    void inject(Object object);

    ObjectGraph getObjectGraph();
}
