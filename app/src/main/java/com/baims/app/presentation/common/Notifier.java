package com.baims.app.presentation.common;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Radwa Elsahn on 2/5/2019.
 */
public class Notifier<T> {
    private final Subject<T, T> subject;

    protected Notifier() {
        this(PublishSubject.<T>create());
    }

    protected Notifier(Subject<T, T> subject) {
        this.subject = subject;
    }

    public <E extends T> void post(E event) {
        subject.onNext(event);
    }

    public Subscription subscribe(final Action1<? super T> action) {
        return subject.subscribe(action);
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
        return subject.ofType(eventClass);//pass only events of specified type, filter all other
    }

    public boolean hasObservers() {
        return subject.hasObservers();
    }
}
