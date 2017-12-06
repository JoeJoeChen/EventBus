/*
 * Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.greenrobot.eventbus;

/**
 * Each subscriber method has a thread mode, which determines in which thread the method is to be called by EventBus.
 * EventBus takes care of threading independently from the posting thread.
 *
 * @see EventBus#register(Object)
 * @author Markus
 */
public enum ThreadMode {
    /**
     * Subscriber will be called directly in the same thread, which is posting the event. This is the default. Event delivery
     * implies the least overhead because it avoids thread switching completely. Thus this is the recommended mode for
     * simple tasks that are known to complete in a very short time without requiring the main thread. Event handlers
     * using this mode must return quickly to avoid blocking the posting thread, which may be the main thread.
     */
    /**
     * 订阅者 将在发布者的线程里 直接被调用。这是默认值。避免了线程切换意味着更少的开销。
     * 因此 这是针对(已知在短时间内的处理，不需要在主线程里的)的情况下的推荐做法。
     * 事件处理，使用这个模式，需要在很短时间内完成，防止调用方在主线程，可能造成堵塞。
     */
    POSTING,

    /**
     * On Android, subscriber will be called in Android's main thread (UI thread). If the posting thread is
     * the main thread, subscriber methods will be called directly, blocking the posting thread. Otherwise the event
     * is queued for delivery (non-blocking). Subscribers using this mode must return quickly to avoid blocking the main thread.
     * If not on Android, behaves the same as {@link #POSTING}.
     */
    /**
     * 在android里面，订阅者 将被在主线程里面调用。如果post线程是主线程，那边直接调用订阅者的方法，
     * 阻塞post线程。其他情况下，事件将被被放置到队列里面，订阅者使用这个mode必须很快的返回，避免阻塞主线程。
     * 在非android环境，使用和POSTING一样。
     */
    MAIN,

    /**
     * On Android, subscriber will be called in Android's main thread (UI thread). Different from {@link #MAIN},
     * the event will always be queued for delivery. This ensures that the post call is non-blocking.
     */
    /**
     * 在android上，订阅者将在主线程被调用，不同于MAIN的是，所有的事件都会被放到队列里面传递，这样确保post不会被阻塞。
     */
    MAIN_ORDERED,

    /**
     * On Android, subscriber will be called in a background thread. If posting thread is not the main thread, subscriber methods
     * will be called directly in the posting thread. If the posting thread is the main thread, EventBus uses a single
     * background thread, that will deliver all its events sequentially. Subscribers using this mode should try to
     * return quickly to avoid blocking the background thread. If not on Android, always uses a background thread.
     */
    /**
     * 在android上，订阅者将被会在后台线程里调用。如果不是在主线程里面post的，订阅者 将被在post所在线程里面调用。
     * 如果post线程是主线程，eventbus使用一个单独的后台线程来顺序的传递事件。订阅者使用这个mode也需要在短时间内返回，
     * 避免阻塞后台线程。
     * 如果不是android，总是在后台线程被调用。
     */
    BACKGROUND,

    /**
     * Subscriber will be called in a separate thread. This is always independent from the posting thread and the
     * main thread. Posting events never wait for subscriber methods using this mode. Subscriber methods should
     * use this mode if their execution might take some time, e.g. for network access. Avoid triggering a large number
     * of long running asynchronous subscriber methods at the same time to limit the number of concurrent threads. EventBus
     * uses a thread pool to efficiently reuse threads from completed asynchronous subscriber notifications.
     */
    /**
     * 订阅者将被在独立的线程里面调用，不同于post线程和主线程。使用这种mode, post后不需要等待订阅者调用。
     * 当订阅方法执行需要时间的时候可以使用这个模式，比如网络连接。避免触发大数据量和长时间运行的异步调用方法，
     * 同时限制并发的线程数。evetbus使用线程池来有效从已完成的异步订阅通知中重用线程
     */
    ASYNC
}