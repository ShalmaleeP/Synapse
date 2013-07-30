/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.synapse.transport.utils.sslcert.cache;

public class CacheController implements CacheControllerMBean{

    private ManageableCache cache;
    private CacheManager cacheManager;

    public CacheController(ManageableCache cache, CacheManager cacheManager){
        this.cache = cache;
        this.cacheManager = cacheManager;
    }

    public boolean stopCacheManager() {
        return cacheManager.stop();
    }

    public boolean wakeUpCacheManager() {
        return cacheManager.wakeUpNow();
    }

    public boolean changeCacheManagerDelayMins(int delay){
        return cacheManager.changeDelay(delay);
    }

    public boolean isCacheManagerRunning() {
        return cacheManager.isRunning();
    }

    public int getCacheSize() {
        return cache.getCacheSize();
    }

    public int getCacheManagerDelayMins(){
        return cacheManager.getDelay();
    }
}
