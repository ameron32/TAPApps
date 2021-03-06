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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ameron32.apps.tapnotes.frmk.di.stabbed.ExtendedGraphHelper;

import java.util.List;

public abstract class StabbedBroadcastReceiver extends BroadcastReceiver {

    private final ExtendedGraphHelper mExtendedGraphHelper = new ExtendedGraphHelper();

    @Override
    public final void onReceive(final Context context, final Intent intent) {
        mExtendedGraphHelper.onCreate(context, getModules(), this);

        handleReceive(context, intent);

        mExtendedGraphHelper.onDestroy();
    }

    protected abstract void handleReceive(final Context context, final Intent intent);

    /**
     * A list of modules to use for the individual receiver graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    protected abstract List<Object> getModules();

}
