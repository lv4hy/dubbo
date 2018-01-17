/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.com.caucho.hessian.io.java8;

import com.alibaba.com.caucho.hessian.io.HessianHandle;

import java.io.Serializable;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class ZoneOffsetHandle implements HessianHandle, Serializable {
    private static final long serialVersionUID = 8841589723587858789L;

    private int seconds;

    public ZoneOffsetHandle() {
    }

    public ZoneOffsetHandle(Object o) {
        try {
            Class c = Class.forName("java.time.ZoneOffset");
            Method m = c.getDeclaredMethod("getTotalSeconds");
            this.seconds = (Integer) m.invoke(o);
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object readResolve() {
        try {
            Class c = Class.forName("java.time.ZoneOffset");
            Method m = c.getDeclaredMethod("ofTotalSeconds", int.class);
            return m.invoke(null, seconds);
        } catch (Throwable t) {
            // ignore
        }
        return null;
    }
}
