/**
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.evcache.pool.eureka.connection;

import java.util.List;

import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.KetamaNodeLocator;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.NodeLocator;

/**
 * ConnectionFactory for eureka based implementation.
 *
 * <p>
 * This implementation creates connections where the operation queue is an
 * ArrayBlockingQueue and the read and write queues are unbounded
 * LinkedBlockingQueues.  The <code>Redistribute</code> FailureMode is always
 * used.  If other FailureModes are needed, look at the
 * ConnectionFactoryBuilder.
 *
 * </p>
 */
public class EVCacheConnectionFactory extends BinaryConnectionFactory {

    private final String appName;

    /**
     * Creates an instance of {@link net.spy.memcached.ConnectionFactory} for the given appName, queue length and Ketama Hashing.
     *
     * @param appName - the name of the EVCache app
     * @param len the length of the operation queue
     */
    public EVCacheConnectionFactory(String appName, int len) {
        super(len, BinaryConnectionFactory.DEFAULT_READ_BUFFER_SIZE, HashAlgorithm.KETAMA_HASH);
        this.appName = appName;
    }

    /**
     * returns a instance of {@link KetamaNodeLocator}.
     */
    public NodeLocator createLocator(List<MemcachedNode> list) {
        return new KetamaNodeLocator(list, HashAlgorithm.KETAMA_HASH, new EVCacheKetamaNodeLocatorConfiguration(appName));
    }
}
