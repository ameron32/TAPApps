package com.ameron32.apps.tapnotes.frmk.di.stabbed.mport;
/*
 * Copyright 2013 Philip Schiffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class StabbedSupportListFragment extends ListFragment {

    private final AtomicBoolean mInjected = new AtomicBoolean();

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mInjected.compareAndSet(false, true)) {
            ((StabbedContext) getActivity()).inject(this);
        }
    }

}
